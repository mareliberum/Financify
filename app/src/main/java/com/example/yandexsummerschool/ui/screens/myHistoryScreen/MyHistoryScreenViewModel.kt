package com.example.yandexsummerschool.ui.screens.myHistoryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.dto.Result
import com.example.yandexsummerschool.domain.models.toHistoryItem
import com.example.yandexsummerschool.domain.useCases.GetExpensesUseCase
import com.example.yandexsummerschool.domain.useCases.GetIncomesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.convertDateToIso
import com.example.yandexsummerschool.domain.utils.getStartOfMonth
import com.example.yandexsummerschool.domain.utils.millsToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана истории операций. Управляет загрузкой и состоянием истории [HistoryScreenState].
 */
@HiltViewModel
class MyHistoryScreenViewModel
    @Inject
    constructor(
        private val getExpensesUseCase: GetExpensesUseCase,
        private val getIncomesUseCase: GetIncomesUseCase,
    ) : ViewModel() {
        // Историю чего мы отображаем - доходы или расходы
        private var transactionsType: TransactionType? = null

        private val _startOfPeriod = MutableStateFlow(millsToDate(getStartOfMonth()))
        val startOfPeriod = _startOfPeriod.asStateFlow()

        private val _endOfPeriod = MutableStateFlow(millsToDate(System.currentTimeMillis()))
        val endOfPeriod = _endOfPeriod.asStateFlow()

        private val _myHistoryScreenState = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)
        val myHistoryScreenState = _myHistoryScreenState.asStateFlow()

        fun loadHistory(type: TransactionType) {
            when (type) {
                TransactionType.EXPENSE -> loadExpenseTransactions()
                TransactionType.INCOME -> loadIncomeTransactions()
            }
            transactionsType = type
        }

        fun setStartDate(dateMillis: Long?) {
            _startOfPeriod.value = millsToDate(dateMillis)
            val type = transactionsType
            if (type != null) loadHistory(type)
        }

        fun setEndDate(dateMillis: Long?) {
            _endOfPeriod.value = millsToDate(dateMillis)
            val type = transactionsType
            if (type != null) loadHistory(type)
        }

        // TODO : loadExpenseTransactions и loadIncomeTransactions почти одинаковые, объединить?
        private fun loadExpenseTransactions() {
            viewModelScope.launch {
                _myHistoryScreenState.value = HistoryScreenState.Loading

                when (
                    val result =
                        getExpensesUseCase(
                            1,
                            convertDateToIso(startOfPeriod.value),
                            convertDateToIso(endOfPeriod.value),
                        )
                ) {
                    is Result.Success -> {
                        val expenses = result.data
                        if (expenses.isEmpty()) {
                            _myHistoryScreenState.value = HistoryScreenState.Empty
                        } else {
                            val history = expenses.map { it.toHistoryItem() }
                            val content =
                                HistoryScreenState.Content(
                                    history = history,
                                    expensesSum = calculateSum(expenses),
                                )
                            _myHistoryScreenState.value = content
                        }
                    }

                    is Result.Failure -> {
                        // TODO обработать ошибку
                        _myHistoryScreenState.value =
// 						HistoryScreenState.Error(result.exception.message ?: "Неизвестная ошибка")
                            HistoryScreenState.Empty
                    }
                }
            }
        }

        private fun loadIncomeTransactions() {
            viewModelScope.launch {
                _myHistoryScreenState.value = HistoryScreenState.Loading

                when (
                    val result =
                        getIncomesUseCase(
                            1,
                            convertDateToIso(startOfPeriod.value),
                            convertDateToIso(endOfPeriod.value),
                        )
                ) {
                    is Result.Success -> {
                        val expenses = result.data
                        if (expenses.isEmpty()) {
                            _myHistoryScreenState.value = HistoryScreenState.Empty
                        } else {
                            val history = expenses.map { it.toHistoryItem() }
                            val content =
                                HistoryScreenState.Content(
                                    history = history,
                                    expensesSum = calculateSum(expenses),
                                )
                            _myHistoryScreenState.value = content
                        }
                    }

                    is Result.Failure -> {
                        // TODO обработать ошибку
                        _myHistoryScreenState.value =
// 						HistoryScreenState.Error(result.exception.message ?: "Неизвестная ошибка")
                            HistoryScreenState.Empty
                    }
                }
            }
        }
    }

/**
 * Состояние экрана/компонента для отображения расходов.
 * Используется для управления UI в зависимости от данных:
 * - Показ контента
 * - Загрузка
 * - Пустое состояние
 */
sealed interface HistoryScreenState {
    data class Content(
        val history: List<HistoryItem>,
        val expensesSum: String,
    ) : HistoryScreenState

    data object Loading : HistoryScreenState

    data object Empty : HistoryScreenState
}

data class HistoryItem(
    val lead: String,
    val title: String,
    val sum: String,
    val time: String,
    val subtitle: String? = null,
)
