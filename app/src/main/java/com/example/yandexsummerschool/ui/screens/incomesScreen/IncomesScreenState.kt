package com.example.yandexsummerschool.ui.screens.incomesScreen

import com.example.yandexsummerschool.domain.models.TransactionModel

/**
 * Состояние экрана расходов.
 * Используется для управления UI в зависимости от данных:
 * - Показ контента
 * - Загрузка
 * - Ошибка
 * - Пустое состояние
 */
sealed interface IncomesScreenState {
    data class Content(
        val incomes: List<TransactionModel>,
        val incomeSum: String,
    ) : IncomesScreenState

    data object Loading : IncomesScreenState

    data object Empty : IncomesScreenState

    data class Error(val message: String) : IncomesScreenState
}
