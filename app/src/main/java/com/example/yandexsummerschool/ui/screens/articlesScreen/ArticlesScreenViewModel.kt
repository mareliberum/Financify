package com.example.yandexsummerschool.ui.screens.articlesScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel для экрана статей. Хранит состояние списка статей [ArticlesScreenState].
 */
class ArticlesScreenViewModel : ViewModel() {
    private val mockArticleUiModel =
        ArticleUiModel(
            id = "1",
            categoryName = "Mock category",
            emoji = "💰",
        )
    private val mockArticleList = List(10) { mockArticleUiModel }
    private val _articlesScreenState =
        MutableStateFlow<ArticlesScreenState>(ArticlesScreenState.Content(mockArticleList))
    val articlesScreenState: StateFlow<ArticlesScreenState> = _articlesScreenState
}
