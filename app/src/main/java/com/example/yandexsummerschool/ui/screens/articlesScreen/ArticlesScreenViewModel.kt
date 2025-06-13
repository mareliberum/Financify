package com.example.yandexsummerschool.ui.screens.articlesScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticlesScreenViewModel : ViewModel() {
	private val mockArticle = Article(
		id = "1",
		categoryName = "Mock category",
		emoji = "💰",
	)
	private val mockArticleList = List(10) { mockArticle }
	private val _articleState =
		MutableStateFlow<ArticleState>(ArticleState.Content(mockArticleList))
	val articleState: StateFlow<ArticleState> = _articleState
}

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
