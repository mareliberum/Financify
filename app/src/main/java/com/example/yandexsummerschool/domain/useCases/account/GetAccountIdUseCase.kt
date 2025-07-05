package com.example.yandexsummerschool.domain.useCases.account

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import javax.inject.Inject

class GetAccountIdUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Result<Int> {
        return try {
            when (val result = repository.getAccount()) {
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
