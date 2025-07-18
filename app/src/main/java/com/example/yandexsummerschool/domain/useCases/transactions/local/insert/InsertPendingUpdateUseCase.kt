package com.example.yandexsummerschool.domain.useCases.transactions.local.insert

import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
import javax.inject.Inject

class InsertPendingUpdateUseCase @Inject constructor(
    private val repository: PendingTransactionsRepository,
) {
    suspend operator fun invoke(update: UpdatedTransactionDomainModel){
        repository.insertPendingUpdate(update)
    }
}
