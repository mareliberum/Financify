package com.example.yandexsummerschool.domain.models

import com.example.yandexsummerschool.ui.features.articlesScreen.ArticleUiModel

data class ArticleModel(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)

fun ArticleModel.toUiModel(): ArticleUiModel {
    return ArticleUiModel(
        id.toString(),
        name,
        emoji,
    )
}
