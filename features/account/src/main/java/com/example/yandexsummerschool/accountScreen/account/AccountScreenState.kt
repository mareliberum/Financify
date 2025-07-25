package com.example.yandexsummerschool.accountScreen.account

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
