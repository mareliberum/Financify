package com.example.yandexsummerschool.domain.useCases.transactions

import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {
    suspend operator fun invoke(
        transactionId: Int,
        transaction: CreatedTransactionDomainModel,
    ): Result<TransactionDomainModel> {
        val result = transactionsRepository.updateTransaction(transactionId, transaction)
        return result
    }
}
