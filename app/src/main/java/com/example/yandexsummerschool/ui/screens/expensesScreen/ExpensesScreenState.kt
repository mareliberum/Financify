package com.example.yandexsummerschool.ui.screens.expensesScreen

import com.example.yandexsummerschool.domain.models.TransactionModel

/**
 * Состояние экрана расходов.
 * Используется для управления UI в зависимости от данных:
 * - Показ контента
 * - Загрузка
 * - Ошибка
 * - Пустое состояние
 */
sealed interface ExpensesScreenState {
    data class Content(
        val expenses: List<TransactionModel>,
        val expensesSum: String,
    ) : ExpensesScreenState

    data class Error(val message: String) : ExpensesScreenState

    data object Loading : ExpensesScreenState

    data object Empty : ExpensesScreenState
}
