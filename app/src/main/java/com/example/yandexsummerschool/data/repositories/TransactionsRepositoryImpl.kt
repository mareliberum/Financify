package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.dto.transactions.toTransactionDomainModel
import com.example.yandexsummerschool.data.dto.transactions.toTransactionRequestDto
import com.example.yandexsummerschool.data.local.room.dao.PendingTransactionsDao
import com.example.yandexsummerschool.data.local.room.dao.TransactionsDao
import com.example.yandexsummerschool.data.local.room.entities.toTransactionDomainModel
import com.example.yandexsummerschool.data.local.room.entities.toTransactionEntity
import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import com.example.yandexsummerschool.domain.utils.date.makeFullIsoDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val MAX_CONNECTION_RETRIES = 3
const val RETRY_DELAY = 2000L

/**
 * Реализация [TransactionsRepository]
 * @param api источник данных [ShmrFinanceApi]
 * @param transactionsDao DAO [TransactionsDao]
 */
class TransactionsRepositoryImpl @Inject constructor(
    private val api: ShmrFinanceApi,
    private val transactionsDao: TransactionsDao,
    private val pendingTransactionsDao: PendingTransactionsDao,
) : TransactionsRepository {
    override suspend fun getTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionDomainModel>> =
        try {
            withContext(Dispatchers.IO) {
                val transactionsFromDb =
                    if (startDate != null && endDate != null) {
                        transactionsDao.getAllTransactions(
                            makeFullIsoDate(startDate),
                            makeFullIsoDate(endDate, atEndOfDay = true),
                        )
                    } else {
                        null
                    }
                val response = executeWIthRetries { api.getTransactions(accountId, startDate, endDate) }
                if (response.isSuccessful) {
                    val transactionsList =
                        response.body()
                            ?.map { it.toTransactionDomainModel(lastSyncDate = makeFullIsoDate(System.currentTimeMillis())) }
                            ?: emptyList()
                    transactionsDao.insertAllTransactions(transactionsList.map { it.toTransactionEntity() })
                    Result.Success(transactionsList)
                } else {
                    if (transactionsFromDb != null) {
                        Result.Success(transactionsFromDb.map { it.toTransactionDomainModel() })
                    }
                    val error = parseError(response.errorBody())
                    Result.Failure(Exception(error.message))
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                if (startDate != null && endDate != null) {
                    val transactionsFromDb =
                        transactionsDao.getAllTransactions(makeFullIsoDate(startDate), makeFullIsoDate(endDate, true))
                    return@withContext Result.Success(transactionsFromDb.map { it.toTransactionDomainModel() })
                } else {
                    return@withContext Result.Failure(e)
                }
            }
        }

    override suspend fun getIncomeTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionDomainModel>> {
        return when (val result = getTransactions(accountId, startDate, endDate)) {
            is Result.Success -> Result.Success(result.data.filter { it.isIncome })
            is Result.Failure -> result
        }
    }

    override suspend fun getExpenseTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionDomainModel>> {
        return when (val result = getTransactions(accountId, startDate, endDate)) {
            is Result.Success -> Result.Success(result.data.filter { !it.isIncome })
            is Result.Failure -> result
        }
    }

    override suspend fun postTransaction(transaction: TransactionDomainModel): Result<TransactionDomainModel> {
        val transactionRequestDto = transaction.toTransactionRequestDto()
        return try {
            withContext(Dispatchers.IO) {
                val response = executeWIthRetries { api.postTransaction(transactionRequestDto) }
                if (response.isSuccessful) {
                    Result.Success(transaction)
                } else {
                    val error = parseError(response.errorBody())
                    Result.Failure(Exception(error.message))
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun updateTransaction(
        transaction: UpdatedTransactionDomainModel,
    ): Result<TransactionDomainModel> {
        val transactionRequestDto = transaction.toTransactionRequestDto()
        return try {
            withContext(Dispatchers.IO) {
                val response =
                    executeWIthRetries { api.updateTransaction(transaction.transactionId, transactionRequestDto) }
                if (response.isSuccessful) {
                    val transactionDomainModel =
                        response.body()?.toTransactionDomainModel()
                            ?: return@withContext Result.Failure(Exception("Server error - empty response body"))
                    Result.Success(transactionDomainModel)
                } else {
                    val error = parseError(response.errorBody())
                    Result.Failure(Exception(error.message))
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun deleteTransactionById(transactionId: Int): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val response = executeWIthRetries { api.deleteTransactionById(transactionId) }
                if (response.isSuccessful) {
                    Result.Success(Unit)
                } else {
                    val error = parseError(response.errorBody())
                    Result.Failure(Exception(error.message))
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionDomainModel> =
        try {
            withContext(Dispatchers.IO) {
                val response = executeWIthRetries { api.getTransaction(id) }
                if (response.isSuccessful) {
                    val transaction =
                        response.body()
                            ?.toTransactionDomainModel(lastSyncDate = makeFullIsoDate(System.currentTimeMillis()))
                    if (transaction != null) {
                        transactionsDao.insertTransaction(transaction.toTransactionEntity())
                        Result.Success(transaction)
                    } else {
                        Result.Failure(Exception("Transaction not found"))
                    }
                } else {
                    val transactionFromDb = transactionsDao.getTransactionById(id)
                    if (transactionFromDb != null) {
                        return@withContext Result.Success(transactionFromDb.toTransactionDomainModel())
                    } else {
                        val error = parseError(response.errorBody())
                        return@withContext Result.Failure(Exception(error.message))
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                val transactionFromDb = transactionsDao.getTransactionById(id)
                if (transactionFromDb != null) {
                    return@withContext Result.Success(transactionFromDb.toTransactionDomainModel())
                } else {
                    return@withContext Result.Failure(e)
                }
            }
        }
}
