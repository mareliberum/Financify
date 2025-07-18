package com.example.yandexsummerschool.domain.useCases

import com.example.yandexsummerschool.domain.repositories.UserCurrencyPrefs
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val userCurrencyPrefs: UserCurrencyPrefs,
) {
    suspend operator fun invoke(): String? {
        return userCurrencyPrefs.getCurrency()
    }
}
