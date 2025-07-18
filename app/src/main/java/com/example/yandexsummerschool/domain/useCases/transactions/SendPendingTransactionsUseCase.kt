package com.example.yandexsummerschool.domain.useCases.transactions

import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
import javax.inject.Inject

class SendPendingTransactionsUseCase @Inject constructor(
    private val repository: PendingTransactionsRepository,
) {
    suspend operator fun invoke() {
        repository.sendPendingTransactions()
    }
}
