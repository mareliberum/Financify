package com.example.yandexsummerschool.domain.useCases.account

import android.util.Log
import com.example.yandexsummerschool.domain.models.AccountModel
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import javax.inject.Inject

class UpdateAccountDataUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(accountModel: AccountModel) {
        try {
            repository.updateAccount(accountModel)
        } catch (e: Exception) {
            // TODO
            Log.e("Api exception", e.message.toString())
        }
    }
}
