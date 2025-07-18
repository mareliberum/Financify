package com.example.yandexsummerschool.data.local.room.entities

import com.example.yandexsummerschool.domain.models.GroupedByCategoryTransactions

data class CategorySumResult(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String?,
    val totalAmount: Double,
)

fun CategorySumResult.toGroupedByCategoryTransactions(): GroupedByCategoryTransactions {
    return GroupedByCategoryTransactions(
        categoryId = categoryId.toString(),
        categoryName = categoryName,
        emoji = emoji ?: "",
        amount = totalAmount,
    )
}
