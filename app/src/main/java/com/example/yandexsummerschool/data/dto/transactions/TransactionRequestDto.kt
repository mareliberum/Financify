package com.example.yandexsummerschool.data.dto.transactions
import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel

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

fun UpdatedTransactionDomainModel.toTransactionRequestDto(): TransactionRequestDto {
    return TransactionRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = date,
        comment = comment ?: "",
    )
}

fun CreatedTransactionDomainModel.toTransactionRequestDto(): TransactionRequestDto {
    return TransactionRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = date,
        comment = comment ?: "",
    )
}
