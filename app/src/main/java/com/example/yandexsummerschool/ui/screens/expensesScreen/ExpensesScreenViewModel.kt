package com.example.yandexsummerschool.ui.screens.expensesScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExpensesScreenViewModel : ViewModel() {
	private val mockExpense = Expense(
		id = "1",
		categoryName = "Mock category",
		emoji = "💰",
		amount = "10 000 ₽",
		comment = "Mock comment",
	)
	private val mockExpensesList = List(10) { mockExpense }
	private val mockExpensesSum = "100 000 ₽"
	private val _expensesState =
		MutableStateFlow<ExpensesState>(ExpensesState.Content(mockExpensesList, mockExpensesSum))
	val expensesState: StateFlow<ExpensesState> = _expensesState
}

sealed interface ExpensesState {
	data class Content(
		val expenses: List<Expense>,
		val expensesSum: String,
	) : ExpensesState

	data object Loading : ExpensesState
	data object Empty : ExpensesState
}

data class Expense(
	val id: String,
	val categoryName: String,
	val emoji: String,
	val amount: String,
	val comment: String? = null,
)

data class ExpenseExtended(
	val id: String,
	val accountName: String,
	val categoryName: String,
	val emoji: String,
	val amount: String,
	val comment: String? = null,
	val transactionDate: String,
	val transactionTime: String,
)
