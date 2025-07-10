package com.example.yandexsummerschool.ui.screens.articlesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.toUiModel
import com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана статей. Хранит состояние списка статей [ArticlesScreenState].
 */
class ArticlesScreenViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {
    private val _articlesScreenState =
        MutableStateFlow<ArticlesScreenState>(ArticlesScreenState.Loading)
    val articlesScreenState: StateFlow<ArticlesScreenState> = _articlesScreenState

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            when (val result = getArticlesUseCase()) {
                is Result.Failure -> _articlesScreenState.value = ArticlesScreenState.Empty
                is Result.Success -> {
                    val articles = result.data.map { it.toUiModel() }
                    _articlesScreenState.value = ArticlesScreenState.Content(articles)
                }
            }
        }
    }
}
