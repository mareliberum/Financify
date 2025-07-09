package com.example.yandexsummerschool.ui.common.uiModels

import com.example.yandexsummerschool.domain.models.TransactionDomainModel

data class TransactionUiModel(
    val id: String,
    val categoryName: String,
    val amount: String,
    val date: String = "",
    val time: String = "",
    val isIncome: Boolean,
    val emoji: String? = null,
    val comment: String? = null,
)

fun TransactionDomainModel.toTransactionUiModel(): TransactionUiModel {
    return TransactionUiModel(
        id = id,
        categoryName = categoryName,
        amount = amount,
        date = date,
        time = "time", // TODO fetch time
        isIncome = isIncome,
        emoji = emoji,
        comment = comment,
    )
}
