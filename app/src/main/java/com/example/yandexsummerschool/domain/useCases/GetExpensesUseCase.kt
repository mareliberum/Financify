package com.example.yandexsummerschool.domain.useCases

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.data.repositories.TransactionsRepository
import com.example.yandexsummerschool.domain.models.TransactionModel
import javax.inject.Inject

/**
 * Возвращает список расходных транзакций
 * @param repository [TransactionsRepository]
 */
class GetExpensesUseCase
    @Inject
    constructor(
        private val repository: TransactionsRepository,
    ) {
        suspend operator fun invoke(
            accountId: Int,
            startDate: String? = null,
            endDate: String? = null,
        ): Result<List<TransactionModel>> {
            return try {
                repository.getExpenseTransactions(accountId, startDate, endDate)
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }
