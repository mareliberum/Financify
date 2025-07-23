package com.example.yandexsummerschool.domain.useCases.transactions.local.insert

import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
import javax.inject.Inject

class InsertPendingTransactionToDbUseCase @Inject constructor(
    private val repository: PendingTransactionsRepository,
) {
    suspend operator fun invoke(transaction: CreatedTransactionDomainModel) {
        repository.insertPendingTransaction(transaction)
    }
}
