package com.example.yandexsummerschool.domain.useCases

import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import javax.inject.Inject

class ChangeCurrencyUseCase @Inject constructor(
    private val repository: TransactionsDbRepository,
) {
    suspend operator fun invoke(currency: String) {
        repository.changeCurrency(currency)
    }
}
