package com.example.yandexsummerschool.ui.screens.myHistoryScreen

import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.data.local.UserDelegate
import com.example.yandexsummerschool.domain.models.TransactionModel
import com.example.yandexsummerschool.domain.models.toHistoryItem
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase
import com.example.yandexsummerschool.domain.useCases.incomes.GetIncomesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.date.convertDateToIso
import com.example.yandexsummerschool.domain.utils.date.getStartOfMonth
import com.example.yandexsummerschool.domain.utils.date.millsToDate
import com.example.yandexsummerschool.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана истории операций. Управляет загрузкой и состоянием истории [HistoryScreenState].
 */
@HiltViewModel
class MyHistoryScreenViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getIncomesUseCase: GetIncomesUseCase,
    override val userDelegate: UserDelegate,
    override val getAccountUseCase: GetAccountUseCase,
) : BaseViewModel() {
    private var transactionsType: TransactionType? = null // Историю чего мы отображаем - доходы или расходы

    private val _startOfPeriod = MutableStateFlow(millsToDate(getStartOfMonth()))
    val startOfPeriod = _startOfPeriod.asStateFlow()

    private val _endOfPeriod = MutableStateFlow(millsToDate(System.currentTimeMillis()))
    val endOfPeriod = _endOfPeriod.asStateFlow()

    private val _myHistoryScreenState = MutableStateFlow<HistoryScreenState>(HistoryScreenState.Loading)
    val myHistoryScreenState = _myHistoryScreenState.asStateFlow()

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

    fun loadHistory(type: TransactionType) {
        viewModelScope.launch {
            _myHistoryScreenState.value = HistoryScreenState.Loading
            transactionsType = type
            when (val result = getResult(type)) {
                is Result.Success -> {
                    val expenses = result.data
                    if (expenses.isEmpty()) {
                        _myHistoryScreenState.value = HistoryScreenState.Empty
                    } else {
                        val history = expenses.map { it.toHistoryItem() }
                        val content =
                            HistoryScreenState.Content(
                                history = history,
                                sum = calculateSum(expenses),
                            )
                        _myHistoryScreenState.value = content
                    }
                }

                is Result.Failure -> {
                    _myHistoryScreenState.value = HistoryScreenState.Empty
                }
            }
        }
    }

    private suspend fun getResult(type: TransactionType): Result<List<TransactionModel>> {
        return when (type) {
            TransactionType.INCOME -> {
                getIncomesUseCase(
                    getAccountId(),
                    convertDateToIso(startOfPeriod.value),
                    convertDateToIso(endOfPeriod.value),
                )
            }

            TransactionType.EXPENSE -> {
                getExpensesUseCase(
                    getAccountId(),
                    convertDateToIso(startOfPeriod.value),
                    convertDateToIso(endOfPeriod.value),
                )
            }
        }
    }
}
