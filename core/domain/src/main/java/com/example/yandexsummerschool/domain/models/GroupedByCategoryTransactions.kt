package com.example.yandexsummerschool.domain.models

data class GroupedByCategoryTransactions(
    val categoryId: String,
    val categoryName: String,
    val emoji: String,
    val amount: Double,
)
