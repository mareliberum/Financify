package com.example.yandexsummerschool.domain.useCases.transactions

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository

class UpdateTransactionUseCase(
    private val transactionsRepository: TransactionsRepository,
) {
    suspend operator fun invoke(transaction: TransactionDomainModel) {
        transactionsRepository.updateTransaction(transaction)
    }
}
