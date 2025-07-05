package com.example.yandexsummerschool.ui.screens.expensesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.UserDelegate
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.date.millisToIso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана расходов. Загружает и хранит состояние расходов [ExpensesScreenState].
 */
@HiltViewModel
class ExpensesScreenViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val userDelegate: UserDelegate,
) : ViewModel() {
    private var fetchJob: Job? = null

    private val _expensesScreenState = MutableStateFlow<ExpensesScreenState>(ExpensesScreenState.Loading)
    val expensesScreenState: StateFlow<ExpensesScreenState> = _expensesScreenState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadExpenses()
    }

    fun loadExpenses() {
        fetchJob?.cancel()
        _isRefreshing.value = true
        fetchJob =
            viewModelScope.launch {
                when (
                    val result =
                        getExpensesUseCase(
                            accountId = getAccountId(),
                            // текущее время в мс минус 24 часа
                            startDate = millisToIso(System.currentTimeMillis() - 24 * 60 * 60 * 1000),
                            endDate = millisToIso(System.currentTimeMillis()),
                        )
                ) {
                    is Result.Success -> {
                        val expenses = result.data
                        if (expenses.isEmpty()) {
                            _expensesScreenState.value = ExpensesScreenState.Empty
                        } else {
                            val sum = calculateSum(expenses)
                            _expensesScreenState.value = ExpensesScreenState.Content(expenses, sum)
                        }
                    }

                    is Result.Failure -> {
                        _expensesScreenState.value =
                            ExpensesScreenState.Error(result.exception.message ?: "Неизвестная ошибка")
                    }
                }
                _isRefreshing.value = false
            }
    }
//
//    fun isCurrencyUpdated(): Boolean{
//        if(userDelegate.getCurrency() != )
//    }

    suspend fun getAccountId(): Int {
        val id = userDelegate.getAccountId() ?: fetchAndSaveAccountId()
        return id
    }

    suspend fun fetchAndSaveAccountId(): Int {
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

    suspend fun getCurrency(): String {
        val currency = userDelegate.getCurrency() ?: fetchAndSaveCurrency()
        return currency
    }

    suspend fun fetchAndSaveCurrency(): String {
        when (val result = getAccountUseCase()) {
            is Result.Failure -> error(result)
            is Result.Success -> {
                val id = result.data.id
                val currency = result.data.currency
                userDelegate.saveAccountId(id)
                userDelegate.saveCurrency(currency)
                return currency
            }
        }
    }
}
