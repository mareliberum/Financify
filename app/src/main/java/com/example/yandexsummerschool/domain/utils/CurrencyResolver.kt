package com.example.yandexsummerschool.domain.utils

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