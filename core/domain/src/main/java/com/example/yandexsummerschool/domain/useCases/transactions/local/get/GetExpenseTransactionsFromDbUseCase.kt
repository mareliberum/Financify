package com.example.yandexsummerschool.domain.useCases.transactions.local.get

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import javax.inject.Inject

class GetExpenseTransactionsFromDbUseCase @Inject constructor(
    private val repository: TransactionsDbRepository,
) {
    suspend operator fun invoke(startDate: String, endDate: String): List<TransactionDomainModel> {
        return repository.getExpenseTransactionsByPeriod(startDate, endDate)
    }
}
