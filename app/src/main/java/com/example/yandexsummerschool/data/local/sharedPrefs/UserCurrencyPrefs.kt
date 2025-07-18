package com.example.yandexsummerschool.data.local.sharedPrefs

interface UserCurrencyPrefs {
    suspend fun getCurrency(): String?

    suspend fun saveCurrency(currency: String)
}
