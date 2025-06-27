package com.example.yandexsummerschool.domain.utils

/**
 * Enum класс валют и символов
 * - Разрешает код валюты в символ
 * @property code код валюты по типу ("RUB")
 * @property symbol символ валюты ("₽")
 */
enum class CurrencyResolver(
    val code: String,
    val symbol: String,
) {
    RUB("RUB", "₽"),
    USD("USD", "$"),
    EUR("EUR", "€"),
    ;

    companion object {
        fun resolve(code: String): String {
            return entries.find { it.code == code }?.symbol ?: "?"
        }
    }
}
