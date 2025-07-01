package com.example.yandexsummerschool.ui.screens.accountScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel для экрана аккаунта. Хранит состояние аккаунта пользователя [AccountScreenState].
 */
class AccountScreenViewModel : ViewModel() {
    private val mockBalance =
        AccountScreenState.Content(
            "Mock account",
            "100 000",
            "RUB",
        )
    private val _accountState = MutableStateFlow(mockBalance)
    val accountState: StateFlow<AccountScreenState> = _accountState
}
