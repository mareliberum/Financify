package com.example.yandexsummerschool.ui.features.expensesScreen

import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.local.UserDelegate
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.date.millisToIso
import com.example.yandexsummerschool.ui.common.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана расходов. Загружает и хранит состояние расходов [ExpensesScreenState].
 */
class ExpensesScreenViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    override val getAccountUseCase: GetAccountUseCase,
    override val userDelegate: UserDelegate,
) : BaseViewModel() {
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
}
