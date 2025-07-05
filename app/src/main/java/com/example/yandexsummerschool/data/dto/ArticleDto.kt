package com.example.yandexsummerschool.data.dto

import com.example.yandexsummerschool.domain.models.ArticleModel

data class ArticleDto(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)

fun ArticleDto.toArticleModel(): ArticleModel {
    return ArticleModel(
        id,
        name,
        emoji,
        isIncome,
    )
}
