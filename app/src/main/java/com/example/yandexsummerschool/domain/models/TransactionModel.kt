package com.example.yandexsummerschool.domain.models

import com.example.yandexsummerschool.domain.utils.formatDate
import com.example.yandexsummerschool.ui.screens.myHistoryScreen.HistoryItem

data class TransactionModel(
    val id: String,
    val categoryName: String,
    val amount: String,
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
        time = formatDate(date),
        subtitle = comment,
    )
}
