package com.example.yandexsummerschool.ui.common.uiModels

import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel
import com.example.yandexsummerschool.domain.utils.date.convertIsoToUiDate
import com.example.yandexsummerschool.domain.utils.date.convertUiDateToIso
import com.example.yandexsummerschool.domain.utils.date.getTimeFromIsoDate

data class TransactionUiModel(
    val id: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: String,
    val currency: String,
    val date: String = "",
    /**HH:mm**/
    val time: String = "",
    val isIncome: Boolean,
    val emoji: String? = null,
    val comment: String? = null,
)

fun TransactionDomainModel.toTransactionUiModel(): TransactionUiModel {
    return TransactionUiModel(
        id = id,
        categoryId = categoryId,
        categoryName = categoryName,
        amount = amount,
        currency = currency,
        date = convertIsoToUiDate(date),
        time = getTimeFromIsoDate(date),
        isIncome = isIncome,
        emoji = emoji,
        comment = comment,
    )
}

fun TransactionUiModel.toTransactionDomainModel(): TransactionDomainModel {
    return TransactionDomainModel(
        id = id,
        categoryId = categoryId,
        categoryName = categoryName,
        amount = amount,
        currency = currency,
        date = convertUiDateToIso(date, time),
        isIncome = isIncome,
        emoji = emoji,
        comment = comment,
    )
}

fun TransactionUiModel.toCreatedTransactionDomainModel(accountId: Int): CreatedTransactionDomainModel {
    return CreatedTransactionDomainModel(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        comment = comment,
        date = convertUiDateToIso(date, time),
    )
}

fun TransactionUiModel.toUpdatedTransactionDomainModel(transactionId: Int, accountId: Int): UpdatedTransactionDomainModel {
    return UpdatedTransactionDomainModel(
        transactionId = transactionId,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        comment = comment,
        date = convertUiDateToIso(date, time),
    )
}
