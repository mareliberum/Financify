package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.models.AccountModel

interface AccountRepository {
    suspend fun getAccount(): Result<AccountModel>

    suspend fun updateAccount(accountModel: AccountModel): Result<AccountModel>
}
