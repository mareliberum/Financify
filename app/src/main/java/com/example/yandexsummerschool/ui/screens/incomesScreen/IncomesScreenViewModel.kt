package com.example.yandexsummerschool.ui.screens.incomesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.dto.Result
import com.example.yandexsummerschool.domain.models.TransactionModel
import com.example.yandexsummerschool.domain.useCases.GetIncomesUseCase
import com.example.yandexsummerschool.domain.utils.calculateSum
import com.example.yandexsummerschool.domain.utils.millisToIso
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomesScreenViewModel @Inject constructor(
	private val getIncomesUseCase: GetIncomesUseCase,
) : ViewModel() {
	private val _incomeState =
		MutableStateFlow<IncomeState>(IncomeState.Loading)
	val incomesState: StateFlow<IncomeState> = _incomeState

	init {
		loadIncomes()
	}

	private fun loadIncomes() {
		viewModelScope.launch {
			_incomeState.value = IncomeState.Loading

			when (val result = getIncomesUseCase(
				accountId = 1,
				startDate = millisToIso(System.currentTimeMillis() - 24 * 60 * 60 * 1000), //минус 24 часа
				endDate = millisToIso(System.currentTimeMillis())
			)
			) {
				is Result.Success -> {
					val incomes = result.data
					if (incomes.isEmpty()) _incomeState.value = IncomeState.Empty
					else {
						val sum = calculateSum(incomes)
						_incomeState.value = IncomeState.Content(incomes, sum)
					}
				}

				is Result.Failure -> {
					_incomeState.value =
						IncomeState.Error(result.exception.message ?: "Неизвестная ошибка")
				}
			}
		}
	}
}

sealed interface IncomeState {
	data class Content(
		val incomes: List<TransactionModel>,
		val incomeSum: String,
	) : IncomeState

	data object Loading : IncomeState
	data object Empty : IncomeState
	data class Error(val message: String) : IncomeState
}


