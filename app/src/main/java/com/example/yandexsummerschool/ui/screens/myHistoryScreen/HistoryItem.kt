package com.example.yandexsummerschool.ui.screens.myHistoryScreen

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.utils.date.convertIsoToDate

data class HistoryItem(
    val lead: String,
    val title: String,
    val sum: String,
    val currency: String,
    val time: String,
    val subtitle: String? = null,
)

fun TransactionDomainModel.toHistoryItem(): HistoryItem {
    return HistoryItem(
        lead = emoji ?: "",
        title = categoryName,
        sum = amount,
        currency = currency,
        time = convertIsoToDate(date),
        subtitle = comment,
    )
}

