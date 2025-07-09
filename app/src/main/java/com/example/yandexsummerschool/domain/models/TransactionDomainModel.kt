package com.example.yandexsummerschool.domain.models

data class TransactionDomainModel(
    val id: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: String,
    val currency: String,
    val isIncome: Boolean,
    val emoji: String? = null,
    val comment: String? = null,
    val date: String = "",
)
