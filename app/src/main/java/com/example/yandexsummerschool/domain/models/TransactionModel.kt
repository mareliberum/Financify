package com.example.yandexsummerschool.domain.models

import com.example.yandexsummerschool.data.dto.TransactionDto
import com.example.yandexsummerschool.domain.utils.date.convertIsoToDate
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.HistoryItem

data class TransactionModel(
    val id: String,
    val categoryName: String,
    val amount: String,
    val currency: String,
    val isIncome: Boolean,
    val emoji: String? = null,
    val comment: String? = null,
    val date: String = "",
)

fun TransactionModel.toHistoryItem(): HistoryItem {
    return HistoryItem(
        lead = emoji ?: "",
        title = categoryName,
        sum = amount,
        currency = currency,
        time = convertIsoToDate(date),
        subtitle = comment,
    )
}

fun TransactionDto.toTransactionModel(): TransactionModel {
    return TransactionModel(
        id = id.toString(),
        categoryName = category.name,
        emoji = category.emoji,
        amount = amount,
        currency = account.currency,
        comment = comment,
        isIncome = category.isIncome,
        date = transactionDate,
    )
}
