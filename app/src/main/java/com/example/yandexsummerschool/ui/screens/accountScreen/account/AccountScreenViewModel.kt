package com.example.yandexsummerschool.ui.screens.accountScreen.account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.account.UpdateAccountDataUseCase
import com.example.yandexsummerschool.domain.utils.Currencies
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана аккаунта. Хранит состояние аккаунта пользователя [AccountScreenState].
 */
class AccountScreenViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountDataUseCase: UpdateAccountDataUseCase,
) : ViewModel() {
    private var getAccountJob: Job? = null

    private val _accountState = MutableStateFlow<AccountScreenState>(AccountScreenState.Loading)
    val accountState: StateFlow<AccountScreenState> = _accountState

    private val _accountTitle = MutableStateFlow("")
    val accountTitle: StateFlow<String> = _accountTitle

    private val _currency = mutableStateOf(Currencies.RUB.code)
    val currency: State<String> = _currency

    init {
        loadAccountData()
    }

    fun loadAccountData() {
        getAccountJob?.cancel()
        _accountState.value = AccountScreenState.Loading
        getAccountJob =
            viewModelScope.launch {
                when (val result = getAccountUseCase()) {
                    is Result.Success -> {
                        val accountModel = result.data
                        _accountTitle.value = accountModel.name
                        _accountState.value = AccountScreenState.Content(accountModel)
                        _currency.value = accountModel.currency
                    }

                    is Result.Failure -> {
                        _accountState.value = AccountScreenState.Error(result.exception.message ?: "Неизвестная ошибка")
                    }
                }
            }
    }

    fun updateCurrency(newValue: String) {
        _currency.value = newValue
        sendUpdates()
    }

    private fun sendUpdates() {
        viewModelScope.launch {
            val currentState = _accountState.value
            if (currentState is AccountScreenState.Content) {
                val newAccountModel =
                    currentState.model.copy(
                        currency = currency.value,
                    )
                updateAccountDataUseCase(newAccountModel)
            }
        }
    }
}
