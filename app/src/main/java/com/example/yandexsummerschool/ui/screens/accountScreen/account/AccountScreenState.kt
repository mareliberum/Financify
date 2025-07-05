package com.example.yandexsummerschool.ui.screens.accountScreen.account

import com.example.yandexsummerschool.domain.models.AccountModel

/**
 * Состояния экрана аккаунта: содержимое, загрузка, пусто.
 */
sealed interface AccountScreenState {
    data class Content(
        val model: AccountModel,
    ) : AccountScreenState

    data class Error(val message: String) : AccountScreenState

    data object Loading : AccountScreenState

    data object Empty : AccountScreenState
}
