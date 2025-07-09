package com.example.yandexsummerschool.data.dto.transactions

import com.example.yandexsummerschool.domain.models.TransactionDomainModel

data class TransactionRequestDto(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String,
)

fun TransactionDomainModel.toTransactionRequestDto(): TransactionRequestDto {
    return TransactionRequestDto(
        accountId = id.toInt(),
        categoryId = categoryId,
        amount = amount,
        transactionDate = date,
        comment = comment ?: "",
    )
}
