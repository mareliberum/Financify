package com.example.yandexsummerschool.ui.screens.incomesScreen

import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.data.local.UserDelegate
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.incomes.GetIncomesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.date.millisToIso
import com.example.yandexsummerschool.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана доходов. Загружает и хранит состояние доходов [IncomesScreenState].
 */
@HiltViewModel
class IncomesScreenViewModel @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase,
    override val userDelegate: UserDelegate,
    override val getAccountUseCase: GetAccountUseCase,
) : BaseViewModel() {
    private val _incomeState = MutableStateFlow<IncomesScreenState>(IncomesScreenState.Loading)
    val incomesState: StateFlow<IncomesScreenState> = _incomeState

    init {
        loadIncomes()
    }

    fun loadIncomes() {
        viewModelScope.launch {
            when (
                val result =
                    getIncomesUseCase(
                        accountId = getAccountId(),
                        // Текущее время в мс минус 24 часа.
                        startDate = millisToIso(System.currentTimeMillis() - 24 * 60 * 60 * 1000),
                        endDate = millisToIso(System.currentTimeMillis()),
                    )
            ) {
                is Result.Success -> {
                    val incomes = result.data
                    if (incomes.isEmpty()) {
                        _incomeState.value = IncomesScreenState.Empty
                    } else {
                        val sum = calculateSum(incomes)
                        _incomeState.value = IncomesScreenState.Content(incomes, sum)
                    }
                }

                is Result.Failure -> {
                    _incomeState.value =
                        IncomesScreenState.Error(result.exception.message ?: "Неизвестная ошибка")
                }
            }
        }
    }
}
