package com.example.yandexsummerschool.features.accountScreen.editor

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.account.UpdateAccountDataUseCase
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.accountScreen.account.AccountScreenState
import com.example.yandexsummerschool.ui.common.ErrorMessageResolver
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditorAccountScreenViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountDataUseCase: UpdateAccountDataUseCase,
) : ViewModel() {
    private var getAccountJob: Job? = null
    private val _accountState = MutableStateFlow<AccountScreenState>(AccountScreenState.Loading)
    val accountState: StateFlow<AccountScreenState> = _accountState
    private val _accountName = mutableStateOf("")
    val accountName: State<String> = _accountName
    private val _balance = mutableStateOf("")
    val balance: State<String> = _balance
    private val _currency = mutableStateOf(Currencies.RUB.code)
    val currency: State<String> = _currency

    init {
        loadAccountData()
    }

    private fun loadAccountData() {
        getAccountJob?.cancel()
        _accountState.value = AccountScreenState.Loading
        getAccountJob =
            viewModelScope.launch {
                when (val result = getAccountUseCase()) {
                    is Result.Success -> {
                        val accountModel = result.data
                        _accountName.value = accountModel.name
                        _balance.value = accountModel.balance
                        _accountState.value = AccountScreenState.Content(accountModel)
                        _currency.value = accountModel.currency
                    }

                    is Result.Failure -> {
                        _accountState.value =
                            AccountScreenState.Error(ErrorMessageResolver.resolve(result.exception))
                    }
                }
            }
    }

    fun updateAccountName(newValue: String) {
        _accountName.value = newValue
    }

    fun updateBalance(newValue: String) {
        _balance.value = newValue
    }

    fun updateCurrency(newValue: String) {
        _currency.value = newValue
    }

    fun confirmUpdates() {
        viewModelScope.launch {
            val currentState = _accountState.value
            if (currentState is AccountScreenState.Content) {
                val newAccountModel =
                    currentState.model.copy(
                        name = accountName.value,
                        balance = balance.value,
                        currency = currency.value,
                    )
                updateAccountDataUseCase(newAccountModel)
            }
        }
    }
}
