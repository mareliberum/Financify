package com.example.yandexsummerschool.domain.repositories

interface UserCurrencyPrefs {
    suspend fun getCurrency(): String?

    suspend fun saveCurrency(currency: String)
}
