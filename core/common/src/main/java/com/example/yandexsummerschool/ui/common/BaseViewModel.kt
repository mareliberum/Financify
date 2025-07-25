package com.example.yandexsummerschool.ui.common

import androidx.lifecycle.ViewModel
import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase

abstract class BaseViewModel : ViewModel() {
    abstract val userDelegate: UserDelegate
    abstract val getAccountUseCase: GetAccountUseCase

    // TODO - в UseCase
    suspend fun getAccountId(): Int {
        val id = userDelegate.getAccountId() ?: fetchAndSaveAccountId()
        return id
    }

    private suspend fun fetchAndSaveAccountId(): Int {
        when (val result = getAccountUseCase()) {
            is Result.Failure -> error(result)
            is Result.Success -> {
                val id = result.data.id
                val currency = result.data.currency
                userDelegate.saveAccountId(id)
                userDelegate.saveCurrency(currency)
                return id
            }
        }
    }
}
