package com.example.yandexsummerschool.data.repositories.transactions

import com.example.yandexsummerschool.data.dto.transactions.toTransactionRequestDto
import com.example.yandexsummerschool.data.local.room.dao.PendingTransactionsDao
import com.example.yandexsummerschool.data.local.room.entities.toCreatedTransactionDomainModel
import com.example.yandexsummerschool.data.local.room.entities.toPendingTransactionEntity
import com.example.yandexsummerschool.data.local.room.entities.toPendingTransactionUpdateEntity
import com.example.yandexsummerschool.data.local.room.entities.toUpdatedTransactionDomainModel
import com.example.yandexsummerschool.data.repositories.executeWIthRetries
import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
import com.example.yandexsummerschool.domain.utils.date.compareIsoDates
import com.example.yandexsummerschool.domain.utils.date.makeFullIsoDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PendingTransactionsRepositoryImpl @Inject constructor(
    private val dao: PendingTransactionsDao,
    private val api: ShmrFinanceApi,
) : PendingTransactionsRepository {
    override suspend fun insertPendingTransaction(createdTransactionDomainModel: CreatedTransactionDomainModel) {
        withContext(Dispatchers.IO) {
            dao.insertPendingTransaction(createdTransactionDomainModel.toPendingTransactionEntity())
        }
    }

    override suspend fun getPendingTransactions(): Result<List<CreatedTransactionDomainModel>> {
        return try {
            withContext(Dispatchers.IO) {
                val transactions = dao.getAllPendingTransactions().map { it.toCreatedTransactionDomainModel() }
                Result.Success(transactions)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun sendPendingTransactions() {
        withContext(Dispatchers.IO) {
            val pendingTransactions = getPendingTransactions()
            if (pendingTransactions is Result.Success) {
                pendingTransactions.data.forEach {
                    val result = postPendingTransactions(it)
                    if (result is Result.Success) {
                        dao.deletePendingTransaction(it.id)
                    }
                }
            }
            val pendingUpdates = getPendingUpdates()
            if (pendingUpdates is Result.Success) {
                pendingUpdates.data.forEach {
                    val result = updatePendingTransactions(it)
                    if (result is Result.Success) {
                        dao.deletePendingUpdates(it.transactionId)
                    }
                }
            }
        }
    }

    override suspend fun postPendingTransactions(transaction: CreatedTransactionDomainModel): Result<Boolean> {
        val transactionRequestDto = transaction.toTransactionRequestDto()
        return try {
            withContext(Dispatchers.IO) {
                val response = executeWIthRetries { api.postTransaction(transactionRequestDto) }
                if (response.isSuccessful) {
                    return@withContext Result.Success(true)
                } else {
                    val error = parseError(response.errorBody())
                    return@withContext Result.Failure(Exception(error.message))
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getPendingUpdates(): Result<List<UpdatedTransactionDomainModel>> {
        return try {
            withContext(Dispatchers.IO) {
                val result = dao.getPendingUpdates().map { it.toUpdatedTransactionDomainModel() }
                Result.Success(result)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun insertPendingUpdate(update: UpdatedTransactionDomainModel) {
        withContext(Dispatchers.IO) {
            val updatedAt = makeFullIsoDate(System.currentTimeMillis())
            dao.insertPendingUpdates(update.toPendingTransactionUpdateEntity(updatedAt))
        }
    }

    override suspend fun updatePendingTransactions(update: UpdatedTransactionDomainModel): Result<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                val transactionRequestDto = update.toTransactionRequestDto()
                if (!checkIfUpdateActual(update)) return@withContext Result.Success(false)
                val response =
                    executeWIthRetries { api.updateTransaction(update.transactionId, transactionRequestDto) }
                if (response.isSuccessful) {
                    Result.Success(true)
                } else {
                    val error = parseError(response.errorBody())
                    Result.Failure(Exception(error.message))
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    /**
     * Проверяет, актуальна ли информация для обновления на случай конфликта с сервером
     */
    private suspend fun checkIfUpdateActual(update: UpdatedTransactionDomainModel): Boolean {
        val transactionToUpdate = api.getTransaction(update.transactionId)
        if (transactionToUpdate.isSuccessful) {
            val updatedAtServer = transactionToUpdate.body()?.updatedAt ?: ""
            val updatedLocally = update.updatedAt
            return compareIsoDates(updatedLocally, updatedAtServer) > 0
        }
        return true
    }
}
