package com.example.yandexsummerschool.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yandexsummerschool.domain.models.ArticleModel

@Entity
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)

fun CategoryEntity.toArticleModel(): ArticleModel {
    return ArticleModel(
        id,
        name,
        emoji,
        isIncome,
    )
}

fun ArticleModel.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id,
        name,
        emoji,
        isIncome,
    )
}
