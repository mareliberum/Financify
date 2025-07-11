package com.example.yandexsummerschool.ui.features.myHistoryScreen

/**
 * Состояние экрана/компонента для отображения расходов.
 * Используется для управления UI в зависимости от данных:
 * - Показ контента
 * - Загрузка
 * - Пустое состояние
 */
sealed interface HistoryScreenState {
    data class Content(
        val history: List<HistoryItem>,
        val sum: String,
    ) : HistoryScreenState

    data object Loading : HistoryScreenState

    data object Empty : HistoryScreenState
}
