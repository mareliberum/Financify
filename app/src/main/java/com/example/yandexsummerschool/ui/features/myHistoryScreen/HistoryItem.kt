package com.example.yandexsummerschool.ui.features.myHistoryScreen

import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.utils.date.convertIsoToUiDate
import com.example.yandexsummerschool.domain.utils.date.getTimeFromIsoDate

data class HistoryItem(
    val id: String,
    val lead: String,
    val title: String,
    val sum: String,
    val currency: String,
    val date: String,
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
        date = convertIsoToUiDate(date),
        time = getTimeFromIsoDate(date),
        subtitle = comment,
    )
}
