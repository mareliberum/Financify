package com.example.yandexsummerschool.features.accountScreen.account

import com.example.yandexsummerschool.domain.models.AccountModel

/**
 * Состояния экрана аккаунта: содержимое, загрузка, пусто.
 */
sealed interface AccountScreenState {
    data class Content(
	    val model: com.example.yandexsummerschool.domain.models.AccountModel,
    ) : AccountScreenState

    data class Error(val message: String) : AccountScreenState

    data object Loading : AccountScreenState

    data object Empty : AccountScreenState
}
