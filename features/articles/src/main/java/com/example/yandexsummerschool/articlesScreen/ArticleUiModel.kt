package com.example.yandexsummerschool.articlesScreen

import com.example.yandexsummerschool.domain.models.ArticleModel

data class ArticleUiModel(
    val id: String,
    val categoryName: String,
    val emoji: String,
)

fun ArticleModel.toUiModel(): ArticleUiModel {
    return ArticleUiModel(
        id.toString(),
        name,
        emoji,
    )
}
