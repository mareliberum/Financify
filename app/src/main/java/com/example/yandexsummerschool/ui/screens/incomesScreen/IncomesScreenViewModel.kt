package com.example.yandexsummerschool.ui.screens.incomesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.UserDelegate
import com.example.yandexsummerschool.domain.useCases.account.GetAccountIdUseCase
import com.example.yandexsummerschool.domain.useCases.incomes.GetIncomesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.date.millisToIso
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
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val userDelegate: UserDelegate,
) : ViewModel() {
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

    suspend fun getAccountId(): Int {
        val id = userDelegate.getAccountId() ?: fetchAndSaveAccountId()
        return id
    }

    suspend fun fetchAndSaveAccountId(): Int {
        when (val result = getAccountIdUseCase()) {
            is Result.Failure -> error(result)
            is Result.Success -> {
                val id = result.data
                userDelegate.saveAccountId(id)
                return id
            }
        }
    }
}
