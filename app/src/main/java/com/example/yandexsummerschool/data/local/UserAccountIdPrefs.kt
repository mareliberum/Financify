package com.example.yandexsummerschool.data.local

interface UserAccountIdPrefs {
    suspend fun getAccountId(): Int?

    suspend fun saveAccountId(accountId: Int)
}
