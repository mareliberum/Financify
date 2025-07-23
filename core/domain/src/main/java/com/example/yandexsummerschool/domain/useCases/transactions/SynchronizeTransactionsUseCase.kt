package com.example.yandexsummerschool.domain.useCases.transactions

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import com.example.yandexsummerschool.domain.utils.date.millsToIsoDateSimple
import javax.inject.Inject

/**
 * Загружает все транзакции по аккаунту, которые есть, поэтому начальная дата ставится 0.
 */
class SynchronizeTransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        val result =
            transactionsRepository.getTransactions(
                id,
                millsToIsoDateSimple(0L),
                millsToIsoDateSimple(System.currentTimeMillis()),
            )
        return when (result) {
            is Result.Failure -> Result.Failure(result.exception)
            is Result.Success -> Result.Success(true)
        }
    }
}
