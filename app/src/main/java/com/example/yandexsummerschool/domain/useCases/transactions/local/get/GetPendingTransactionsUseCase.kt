package com.example.yandexsummerschool.domain.useCases.transactions.local.get

import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
import javax.inject.Inject

class GetPendingTransactionsUseCase @Inject constructor(
    private val repository: PendingTransactionsRepository,
) {
    suspend operator fun invoke(): Result<List<CreatedTransactionDomainModel>> {
        return repository.getPendingTransactions()
    }
}
