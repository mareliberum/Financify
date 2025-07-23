package com.example.yandexsummerschool.incomesScreen

import com.example.yandexsummerschool.domain.models.TransactionDomainModel

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
        val incomes: List<TransactionDomainModel>,
        val incomeSum: String,
    ) : IncomesScreenState

    data object Loading : IncomesScreenState

    data object Empty : IncomesScreenState

    data class Error(val message: String) : IncomesScreenState
}
