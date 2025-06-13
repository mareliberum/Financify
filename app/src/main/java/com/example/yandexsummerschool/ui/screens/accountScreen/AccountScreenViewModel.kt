package com.example.yandexsummerschool.ui.screens.accountScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AccountScreenViewModel : ViewModel() {
	private val mockBalance = AccountState.Content(
		"Mock account",
		"100 000",
		"RUB",
	)
	private val _accountState = MutableStateFlow(mockBalance)
	val accountState: StateFlow<AccountState> = _accountState
}

enum class Currency(
	val code: String,
	val symbol: String,
) {
	RUB("RUB", "₽"),
	USD("USD", "$"),
	EUR("EUR", "€");

	companion object {
		fun resolve(code: String): String {
			return entries.find { it.code == code }?.symbol ?: "?"
		}
	}
}

sealed interface AccountState {
	data class Content(
		val name: String,
		val balance: String,
		val currency: String,
	) : AccountState

	data object Loading : AccountState
	data object Empty : AccountState
}