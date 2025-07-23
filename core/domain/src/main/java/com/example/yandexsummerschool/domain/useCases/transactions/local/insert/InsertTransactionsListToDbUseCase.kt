package com.example.yandexsummerschool.domain.useCases.transactions.local.insert

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import javax.inject.Inject

class InsertTransactionsListToDbUseCase @Inject constructor(
    private val repository: TransactionsDbRepository,
) {
    suspend operator fun invoke(transactions: List<TransactionDomainModel>) {
        repository.insertAllTransactions(transactions)
    }
}
