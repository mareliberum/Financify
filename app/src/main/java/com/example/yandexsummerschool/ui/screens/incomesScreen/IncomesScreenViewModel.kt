package com.example.yandexsummerschool.ui.screens.incomesScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IncomesScreenViewModel : ViewModel() {
	private val mockIncome = Income(
		id = "1",
		categoryName = "Зарплата",
		amount = "100 000 ₽"
	)
	private val mockIncomesList = List(1) { mockIncome }
	private val mockIncomeSum = "100 000 ₽"
	private val _incomeState =
		MutableStateFlow<IncomeState>(IncomeState.Content(mockIncomesList, mockIncomeSum))
	val incomesState: StateFlow<IncomeState> = _incomeState
}

sealed interface IncomeState {
	data class Content(
		val incomes: List<Income>,
		val incomeSum: String,
	) : IncomeState

	data object Loading : IncomeState
	data object Empty : IncomeState
}

data class Income(
	val id: String,
	val categoryName: String,
	val amount: String,
	val emoji: String? = null,
	val comment: String? = null,
)