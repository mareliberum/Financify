package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.models.TransactionModel
import com.example.yandexsummerschool.domain.models.toTransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

const val MAX_CONNECTION_RETRIES = 3
const val RETRY_DELAY = 2000L

/**
 * Реализация [TransactionsRepository]
 * @param api источник данных [ShmrFinanceApi]
 */
class TransactionsRepositoryImpl
    @Inject
    constructor(
        private val api: ShmrFinanceApi,
    ) : TransactionsRepository {
        override suspend fun getTransactions(
            accountId: Int,
            startDate: String?,
            endDate: String?,
        ): Result<List<TransactionModel>> =
            withContext(Dispatchers.IO) {
                val response = executeWIthRetries { api.getTransactions(accountId, startDate, endDate) }
                if (response.isSuccessful) {
                    val transactionsList = response.body()?.map { it.toTransactionModel() } ?: emptyList()
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
        ): Result<List<TransactionModel>> {
            return when (val result = getTransactions(accountId, startDate, endDate)) {
                is Result.Success -> Result.Success(result.data.filter { it.isIncome })
                is Result.Failure -> result
            }
        }

        override suspend fun getExpenseTransactions(
            accountId: Int,
            startDate: String?,
            endDate: String?,
        ): Result<List<TransactionModel>> {
            return when (val result = getTransactions(accountId, startDate, endDate)) {
                is Result.Success -> Result.Success(result.data.filter { !it.isIncome })
                is Result.Failure -> result
            }
        }

        private suspend fun <T> executeWIthRetries(
            functionBlock: suspend () -> Response<T>,
        ): Response<T> {
            // Максимум {MAX_CONNECTION_RETRIES} попытки подключится с интервалом {RETRY_DELAY} сек.
            repeat(MAX_CONNECTION_RETRIES) { attempt ->
                val response = functionBlock()
                if (response.isSuccessful) {
                    return response
                } else if (attempt == MAX_CONNECTION_RETRIES - 1) {
                    return response
                } else if (response.code() in 500..599 && attempt < MAX_CONNECTION_RETRIES) {
                    delay(RETRY_DELAY)
                }
            }
            return functionBlock()
        }
    }
