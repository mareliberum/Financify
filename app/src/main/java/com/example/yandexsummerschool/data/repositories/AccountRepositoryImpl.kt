package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.dto.account.AccountUpdateRequestDto
import com.example.yandexsummerschool.data.retrofit.ErrorParser.parseError
import com.example.yandexsummerschool.data.retrofit.ShmrAccountApi
import com.example.yandexsummerschool.data.local.room.dao.AccountDao
import com.example.yandexsummerschool.data.local.room.entities.toAccountEntity
import com.example.yandexsummerschool.data.local.room.entities.toAccountModel
import com.example.yandexsummerschool.domain.models.AccountModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.toAccountModel
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: ShmrAccountApi,
    private val accountDao: AccountDao,
) : AccountRepository {
    override suspend fun getAccountFromApi(): Result<AccountModel> =
        try {
            withContext(Dispatchers.IO) {
                val accountFromDb = accountDao.getAccount()
                val response = executeWIthRetries { accountApi.getAccount() }
                if (response.isSuccessful) {
                    val account =
                        response.body()?.first()?.toAccountModel() ?: return@withContext Result.Failure(
                            Exception(
                                "Empty account data",
                            ),
                        )
                    accountDao.insertAccount(account.toAccountEntity())
                    Result.Success(account)
                } else {
                    if (accountFromDb != null) {
                        Result.Success(accountFromDb.toAccountModel())
                    }
                    val error = parseError(response.errorBody())
                    Result.Failure(Exception(error.message))
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                val accountFromDb = accountDao.getAccount()
                if (accountFromDb != null) {
                    return@withContext Result.Success(accountFromDb.toAccountModel())
                } else {
                    return@withContext Result.Failure(e)
                }
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

    override suspend fun getAccountFromDb(): Result<AccountModel> {
        return withContext(Dispatchers.IO) {
            val account = accountDao.getAccount()
            if (account != null) {
                Result.Success(account.toAccountModel())
            } else {
                Result.Failure(Exception("Empty data base"))
            }
        }
    }
}
