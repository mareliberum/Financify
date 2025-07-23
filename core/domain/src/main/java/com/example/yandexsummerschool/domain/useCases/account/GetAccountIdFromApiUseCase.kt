package com.example.yandexsummerschool.domain.useCases.account

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import javax.inject.Inject

class GetAccountIdFromApiUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Result<Int> {
        return try {
            when (val result = repository.getAccountFromApi()) {
                is Result.Failure -> Result.Failure(result.exception)
                is Result.Success -> {
                    Result.Success(result.data.id)
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
