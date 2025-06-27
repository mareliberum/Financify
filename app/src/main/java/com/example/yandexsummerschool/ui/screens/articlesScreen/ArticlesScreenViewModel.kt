package com.example.yandexsummerschool.ui.screens.articlesScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel для экрана статей. Хранит состояние списка статей [ArticleState].
 */
class ArticlesScreenViewModel : ViewModel() {
    private val mockArticle =
        Article(
            id = "1",
            categoryName = "Mock category",
            emoji = "💰",
        )
    private val mockArticleList = List(10) { mockArticle }
    private val _articleState =
        MutableStateFlow<ArticleState>(ArticleState.Content(mockArticleList))
    val articleState: StateFlow<ArticleState> = _articleState
}

/**
 * Состояние экрана списка статей.
 * Используется для управления UI в зависимости от данных:
 * - Показ контента
 * - Загрузка
 * - Пустое состояние
 */
sealed interface ArticleState {
    data class Content(
        val articles: List<Article>,
    ) : ArticleState

    data object Loading : ArticleState

    data object Empty : ArticleState
}

data class Article(
    val id: String,
    val categoryName: String,
    val emoji: String,
)
