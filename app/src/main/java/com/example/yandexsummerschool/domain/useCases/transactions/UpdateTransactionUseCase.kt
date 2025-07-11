package com.example.yandexsummerschool.domain.useCases.transactions

import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {
    suspend operator fun invoke(transactionId: Int, transaction: CreatedTransactionDomainModel) {
        transactionsRepository.updateTransaction(transactionId, transaction)
    }
}
