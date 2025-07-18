package com.example.yandexsummerschool.data.local.sharedPrefs

interface UserAccountIdPrefs {
    suspend fun getAccountId(): Int?

    suspend fun saveAccountId(accountId: Int)
}
