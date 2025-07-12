package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.AccountModel
import com.example.yandexsummerschool.domain.models.Result

interface AccountRepository {
    suspend fun getAccount(): Result<AccountModel>

    suspend fun updateAccount(accountModel: AccountModel): Result<AccountModel>
}
