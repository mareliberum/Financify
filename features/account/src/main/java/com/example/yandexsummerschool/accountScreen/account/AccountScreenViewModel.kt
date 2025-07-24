package com.example.yandexsummerschool.features.accountScreen.account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.accountScreen.account.AccountScreenState
import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.ChangeCurrencyUseCase
import com.example.yandexsummerschool.domain.useCases.account.GetAccountFromDbUseCase
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.account.GetStatisticsForChart
import com.example.yandexsummerschool.domain.useCases.account.UpdateAccountDataUseCase
import com.example.yandexsummerschool.domain.utils.Currencies
import com.example.yandexsummerschool.ui.common.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана аккаунта. Хранит состояние аккаунта пользователя [AccountScreenState].
 */
class AccountScreenViewModel @Inject constructor(
    private val updateAccountDataUseCase: UpdateAccountDataUseCase,
    private val getAccountFromDbUseCase: GetAccountFromDbUseCase,
    private val changeCurrencyUseCase: ChangeCurrencyUseCase,
    private val getStatisticsForChart: GetStatisticsForChart,
    override val userDelegate: UserDelegate,
    override val getAccountUseCase: GetAccountUseCase,

    ) : BaseViewModel() {
    private var getAccountJob: Job? = null
    private val _accountState = MutableStateFlow<AccountScreenState>(AccountScreenState.Loading)
    val accountState: StateFlow<AccountScreenState> = _accountState
    private val _accountTitle = MutableStateFlow("")
    val accountTitle: StateFlow<String> = _accountTitle
    private val _currency = mutableStateOf(Currencies.RUB.code)
    val currency: State<String> = _currency

    private val _statistics = MutableStateFlow<List<Float>>(emptyList())
    val statistics = _statistics.asStateFlow()

    init {
        loadAccountData()

    }

    fun loadAccountData() {
        getAccountJob?.cancel()
        _accountState.value = AccountScreenState.Loading
        getAccountJob =
            viewModelScope.launch {
                val accountFromDb = getAccountFromDbUseCase()
                if (accountFromDb is Result.Success) {
                    _accountTitle.value = accountFromDb.data.name
                    _accountState.value = AccountScreenState.Content(accountFromDb.data)
                    _currency.value = accountFromDb.data.currency
                }
                when (val result = getAccountUseCase()) {
                    is Result.Success -> {
                        val accountModel = result.data
                        _accountTitle.value = accountModel.name
                        _accountState.value = AccountScreenState.Content(accountModel)
                        _currency.value = accountModel.currency
                    }

                    is Result.Failure -> {
                        if (accountFromDb is Result.Failure) {
                            _accountState.value =
                                AccountScreenState.Error(result.exception.message ?: "Неизвестная ошибка")
                        }
                    }
                }
            }
    }

    fun updateCurrency(newValue: String) {
        _currency.value = newValue
        viewModelScope.launch {
            changeCurrencyUseCase(newValue)
        }
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

    fun getStatistics(){
        viewModelScope.launch {
            val result = getStatisticsForChart(getAccountId())
            println(result) // TODO delete
            if(result is Result.Success){
                _statistics.value = result.data
            }

        }
    }
}
