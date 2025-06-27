package com.example.yandexsummerschool.domain.dto

import com.example.yandexsummerschool.domain.models.TransactionModel
import com.example.yandexsummerschool.domain.utils.CurrencyResolver

data class TransactionDto(
    val id: Int,
    val account: Account,
    val category: Category,
    val amount: String,
    val transactionDate: String,
    val comment: String,
    val createdAt: String,
    val updatedAt: String,
)

data class Account(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)

data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)

fun TransactionDto.toTransactionModel(): TransactionModel {
    return TransactionModel(
        id = id.toString(),
        categoryName = category.name,
        emoji = category.emoji,
        // TODO добавление символа валюты стоит перенести на UI
        amount = amount + " " + CurrencyResolver.resolve(account.currency),
        comment = comment,
        isIncome = category.isIncome,
        date = transactionDate,
    )
}
