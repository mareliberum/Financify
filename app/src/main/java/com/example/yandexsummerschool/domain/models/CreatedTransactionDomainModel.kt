package com.example.yandexsummerschool.domain.models

data class CreatedTransactionDomainModel(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val comment: String? = null,
    val date: String = "",
    val id: Int = 0,
)
