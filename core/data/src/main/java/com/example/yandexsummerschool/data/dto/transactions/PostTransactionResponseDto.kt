package com.example.yandexsummerschool.data.dto.transactions

data class PostTransactionResponseDto(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String,
    val cratedAt: String,
    val updatedAt: String,
)
// fun PostTransactionResponseDto.toTransactionDomainModel(categoryName: String, ): TransactionDomainModel {
//    return TransactionDomainModel(
//        id = id,
//        categoryId = categoryId,
//        categoryName = categoryName,
//        amount = amount,
//        currency =
//    )
// }
