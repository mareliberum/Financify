package com.example.yandexsummerschool.articlesScreen

/**
 * Состояние экрана списка статей.
 * Используется для управления UI в зависимости от данных:
 * - Показ контента
 * - Загрузка
 * - Пустое состояние
 */
sealed interface ArticlesScreenState {
    data class Content(
	    val articleUiModels: List<ArticleUiModel>,
    ) : ArticlesScreenState

    data object Loading : ArticlesScreenState

    data object Empty : ArticlesScreenState
}
