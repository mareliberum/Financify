package com.example.yandexsummerschool.domain.utils

import com.example.yandexsummerschool.domain.models.TransactionModel

fun calculateSum(transactions: List<TransactionModel>): String {
    val total =
        transactions.sumOf {
            it.amount.replace(Regex("[^\\d]"), "").toLongOrNull() ?: 0L
        }
    return "$total"
}
