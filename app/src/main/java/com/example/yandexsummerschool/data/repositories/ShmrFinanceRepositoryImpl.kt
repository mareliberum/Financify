package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.dto.Result
import com.example.yandexsummerschool.domain.dto.toTransactionModel
import com.example.yandexsummerschool.domain.models.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val MAX_CONNECTION_RETRIES = 3
const val RETRY_DELAY = 2000L

/**
 * Реализация [ShmrFinanceRepository]
 * @param api источник данных [ShmrFinanceApi]*
 */
class ShmrFinanceRepositoryImpl
    @Inject
    constructor(
        private val api: ShmrFinanceApi,
    ) : ShmrFinanceRepository {
        override suspend fun getTransactions(
            accountId: Int,
            startDate: String?,
            endDate: String?,
        ): Result<List<TransactionModel>> =
            withContext(Dispatchers.IO) {
                // Максимум 3 попытки подключится с интервалом 2 сек.
                repeat(MAX_CONNECTION_RETRIES) { attempt ->
                    try {
                        val response = api.getTransactions(accountId, startDate, endDate)
                        if (response.isSuccessful) {
                            val transactionsList =
                                response.body()?.map { it.toTransactionModel() } ?: emptyList()
                            return@withContext Result.Success(transactionsList)
                        } else if (response.code() in 500..599 && attempt < MAX_CONNECTION_RETRIES - 1) {
                            delay(RETRY_DELAY)
                        } else {
                            val error = parseError(response.errorBody())
                            return@withContext Result.Failure(Exception(error.message))
                        }
                    } catch (e: Exception) {
                        return@withContext Result.Failure(e)
                    }
                }
                Result.Failure(Exception("Не удалось получить данные после $MAX_CONNECTION_RETRIES попыток"))
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

        override suspend fun getCategories() {
            TODO("Not yet implemented")
        }
    }
