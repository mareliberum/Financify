package com.example.yandexsummerschool.ui.features.myHistoryScreen

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.utils.date.convertIsoToUiDate

data class HistoryItem(
    val id: String,
    val lead: String,
    val title: String,
    val sum: String,
    val currency: String,
    val time: String,
    val subtitle: String? = null,
)

fun TransactionDomainModel.toHistoryItem(): HistoryItem {
    return HistoryItem(
        id = id,
        lead = emoji ?: "",
        title = categoryName,
        sum = amount,
        currency = currency,
        time = convertIsoToUiDate(date),
        subtitle = comment,
    )
}
