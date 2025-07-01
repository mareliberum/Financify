package com.example.yandexsummerschool.ui.screens.expensesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.useCases.GetExpensesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.date.millisToIso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана расходов. Загружает и хранит состояние расходов [ExpensesScreenState].
 */
@HiltViewModel
class ExpensesScreenViewModel
    @Inject
    constructor(
        private val getExpensesUseCase: GetExpensesUseCase,
    ) : ViewModel() {
        private val _expensesScreenState = MutableStateFlow<ExpensesScreenState>(ExpensesScreenState.Loading)
        val expensesScreenState: StateFlow<ExpensesScreenState> = _expensesScreenState

        init {
            loadExpenses()
        }

        private fun loadExpenses() {
            viewModelScope.launch {
                _expensesScreenState.value = ExpensesScreenState.Loading

                when (
                    val result =
                        getExpensesUseCase(
                            accountId = 1,
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
            }
        }
    }
