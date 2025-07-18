package com.example.yandexsummerschool.domain.useCases.expenses

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
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
        ): Result<List<TransactionDomainModel>> {
            return try {
                repository.getExpenseTransactions(accountId, startDate, endDate)
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }
