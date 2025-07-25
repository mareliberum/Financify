package com.example.yandexsummerschool.domain.useCases.account

import com.example.yandexsummerschool.domain.models.AccountModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import javax.inject.Inject

class GetAccountFromDbUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): Result<com.example.yandexsummerschool.domain.models.AccountModel> {
        return repository.getAccountFromDb()
    }
}
