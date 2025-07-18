package com.example.yandexsummerschool.domain.models

data class UpdatedTransactionDomainModel(
    val transactionId: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val comment: String? = null,
    val date: String = "",
)
