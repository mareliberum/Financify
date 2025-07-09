package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.dto.account.AccountUpdateRequestDto
import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrAccountApi
import com.example.yandexsummerschool.domain.models.AccountModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.toAccountModel
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: ShmrAccountApi,
) : AccountRepository {
    override suspend fun getAccount(): Result<AccountModel> =
        withContext(Dispatchers.IO) {
            val response = executeWIthRetries { accountApi.getAccount() }
            if (response.isSuccessful) {
                val account =
                    response.body()?.first()?.toAccountModel() ?: return@withContext Result.Failure(
                        Exception(
                            "Empty account data",
                        ),
                    )
                Result.Success(account)
            } else {
                val error = parseError(response.errorBody())
                Result.Failure(Exception(error.message))
            }
        }

    override suspend fun updateAccount(accountModel: AccountModel): Result<AccountModel> {
        val accountUpdateRequestDto =
            AccountUpdateRequestDto(
                name = accountModel.name,
                balance = accountModel.balance,
                currency = accountModel.currency,
            )
        return withContext(Dispatchers.IO) {
            val response = accountApi.updateAccount(accountModel.id, accountUpdateRequestDto)
            if (response.isSuccessful) {
                val account =
                    response.body()?.toAccountModel()
                        ?: return@withContext Result.Failure(Exception("Empty account data"))
                Result.Success(account)
            } else {
                val error = parseError(response.errorBody())
                Result.Failure(Exception(error.message))
            }
        }
    }
}
