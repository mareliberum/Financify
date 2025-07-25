package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.AccountModel
import com.example.yandexsummerschool.domain.models.Result

interface AccountRepository {
    suspend fun getAccountFromApi(): Result<com.example.yandexsummerschool.domain.models.AccountModel>

    suspend fun updateAccount(accountModel: com.example.yandexsummerschool.domain.models.AccountModel): Result<com.example.yandexsummerschool.domain.models.AccountModel>

    suspend fun getAccountFromDb(): Result<com.example.yandexsummerschool.domain.models.AccountModel>
}
