package com.example.yandexsummerschool.domain.useCases.account

import com.example.yandexsummerschool.domain.models.AccountModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Result<AccountModel> {
        return repository.getAccountFromApi()
    }
}
