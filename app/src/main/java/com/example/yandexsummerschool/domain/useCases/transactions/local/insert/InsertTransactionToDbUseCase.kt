package com.example.yandexsummerschool.domain.useCases.transactions.local.insert

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import javax.inject.Inject

class InsertTransactionToDbUseCase @Inject constructor(
    private val repository: TransactionsDbRepository,
) {
    suspend operator fun invoke(transaction: TransactionDomainModel) {
        repository.insertTransaction(transaction)
    }
}
