package com.example.yandexsummerschool.ui.screens.myHistoryScreen

/**
 * Тип транзакции(расход/доход)
 * @param key [String] ключ соотвествующего параметра
 */
enum class TransactionType(val key: String) {
    INCOME("income"),
    EXPENSE("expense"),
}
