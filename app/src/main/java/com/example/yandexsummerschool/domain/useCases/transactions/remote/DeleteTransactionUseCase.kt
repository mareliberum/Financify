package com.example.yandexsummerschool.domain.useCases.transactions.remote

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {
    suspend operator fun invoke(
        transactionId: Int,
    ): Result<Unit> {
        val result = transactionsRepository.deleteTransactionById(transactionId)
        return result
    }
}
