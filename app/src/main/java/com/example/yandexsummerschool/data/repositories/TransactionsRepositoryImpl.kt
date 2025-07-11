package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.dto.transactions.toTransactionDomainModel
import com.example.yandexsummerschool.data.dto.transactions.toTransactionRequestDto
import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val MAX_CONNECTION_RETRIES = 3
const val RETRY_DELAY = 2000L

/**
 * Реализация [TransactionsRepository]
 * @param api источник данных [ShmrFinanceApi]
 */
class TransactionsRepositoryImpl @Inject constructor(
    private val api: ShmrFinanceApi,
) : TransactionsRepository {
    override suspend fun getTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionDomainModel>> =
        withContext(Dispatchers.IO) {
            val response = executeWIthRetries { api.getTransactions(accountId, startDate, endDate) }
            if (response.isSuccessful) {
                val transactionsList = response.body()?.map { it.toTransactionDomainModel() } ?: emptyList()
                Result.Success(transactionsList)
            } else {
                val error = parseError(response.errorBody())
                Result.Failure(Exception(error.message))
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
        transactionId: Int,
        transaction: CreatedTransactionDomainModel,
    ): Result<TransactionDomainModel> {
        val transactionRequestDto = transaction.toTransactionRequestDto()
        return try {
            withContext(Dispatchers.IO) {
                val response = executeWIthRetries { api.updateTransaction(transactionId, transactionRequestDto) }
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
        withContext(Dispatchers.IO) {
            val response = executeWIthRetries { api.getTransaction(id) }
            if (response.isSuccessful) {
                val transaction = response.body()?.toTransactionDomainModel()
                if (transaction != null) {
                    Result.Success(transaction)
                } else {
                    Result.Failure(Exception("Transaction not found"))
                }
            } else {
                val error = parseError(response.errorBody())
                Result.Failure(Exception(error.message))
            }
        }
}
