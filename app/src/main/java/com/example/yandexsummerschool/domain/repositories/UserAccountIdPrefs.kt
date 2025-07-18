package com.example.yandexsummerschool.domain.repositories

interface UserAccountIdPrefs {
    suspend fun getAccountId(): Int?

    suspend fun saveAccountId(accountId: Int)
}
