package com.example.yandexsummerschool.ui.screens.accountScreen

/**
 * Состояния экрана аккаунта: содержимое, загрузка, пусто.
 */
sealed interface AccountScreenState {
    data class Content(
        val name: String,
        val balance: String,
        val currency: String,
    ) : AccountScreenState

    data object Loading : AccountScreenState

    data object Empty : AccountScreenState
}
