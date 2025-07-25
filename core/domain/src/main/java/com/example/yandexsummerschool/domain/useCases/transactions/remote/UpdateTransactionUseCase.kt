package com.example.yandexsummerschool.domain.useCases.transactions.remote

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {
    suspend operator fun invoke(
        transaction: UpdatedTransactionDomainModel,
    ): Result<TransactionDomainModel> {
        val result = transactionsRepository.updateTransaction(transaction)
        return result
    }
}
