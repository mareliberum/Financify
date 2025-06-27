package com.example.yandexsummerschool.ui.screens.expensesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.dto.Result
import com.example.yandexsummerschool.domain.models.TransactionModel
import com.example.yandexsummerschool.domain.useCases.GetExpensesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.millisToIso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана расходов. Загружает и хранит состояние расходов [ExpensesState].
 */
@HiltViewModel
class ExpensesScreenViewModel
    @Inject
    constructor(
        private val getExpensesUseCase: GetExpensesUseCase,
    ) : ViewModel() {
        private val _expensesState = MutableStateFlow<ExpensesState>(ExpensesState.Loading)
        val expensesState: StateFlow<ExpensesState> = _expensesState

        init {
            loadExpenses()
        }

        private fun loadExpenses() {
            viewModelScope.launch {
                _expensesState.value = ExpensesState.Loading

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
                            _expensesState.value = ExpensesState.Empty
                        } else {
                            val sum = calculateSum(expenses)
                            _expensesState.value = ExpensesState.Content(expenses, sum)
                        }
                    }

                    is Result.Failure -> {
                        _expensesState.value =
                            ExpensesState.Error(result.exception.message ?: "Неизвестная ошибка")
                    }
                }
            }
        }
    }

/**
 * Состояние экрана расходов.
 * Используется для управления UI в зависимости от данных:
 * - Показ контента
 * - Загрузка
 * - Ошибка
 * - Пустое состояние
 */
sealed interface ExpensesState {
    data class Content(
        val expenses: List<TransactionModel>,
        val expensesSum: String,
    ) : ExpensesState

    data class Error(val message: String) : ExpensesState

    data object Loading : ExpensesState

    data object Empty : ExpensesState
}
