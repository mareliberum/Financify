package com.example.yandexsummerschool.data.repositories

import android.util.Log
import com.example.yandexsummerschool.data.dto.transactions.toTransactionRequestDto
import com.example.yandexsummerschool.data.local.room.dao.PendingTransactionsDao
import com.example.yandexsummerschool.data.local.room.entities.toCreatedTransactionDomainModel
import com.example.yandexsummerschool.data.local.room.entities.toPendingTransactionEntity
import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
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
                val transactions = dao.getPendingTransactions().map { it.toCreatedTransactionDomainModel() }
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
        }
    }

    override suspend fun postPendingTransactions(transaction: CreatedTransactionDomainModel): Result<Boolean> {
        val transactionRequestDto = transaction.toTransactionRequestDto()
        return try {
            withContext(Dispatchers.IO) {
                val response = executeWIthRetries { api.postTransaction(transactionRequestDto) }
                if (response.isSuccessful) {
                    Log.d("PendingRepo", "Pending transactions sent ${transaction.id}")
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
}
