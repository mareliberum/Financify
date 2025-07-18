package com.example.yandexsummerschool.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yandexsummerschool.domain.models.TransactionDomainModel

@Entity
data class TransactionEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val amount: String,
    val currency: String,
    val isIncome: Boolean,
    val emoji: String? = null,
    val comment: String? = null,
    val date: String = "",
    val lastSyncDate: String = "",
)

fun TransactionEntity.toTransactionDomainModel(): TransactionDomainModel {
    return TransactionDomainModel(
        id = id.toString(),
        categoryId = categoryId,
        categoryName = categoryName,
        amount = amount,
        currency = currency,
        isIncome = isIncome,
        emoji = emoji,
        comment = comment,
        date = date,
        lastSyncDate = lastSyncDate,
    )
}

fun TransactionDomainModel.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = id.toInt(),
        categoryId = categoryId,
        categoryName = categoryName,
        amount = amount,
        currency = currency,
        isIncome = isIncome,
        emoji = emoji,
        comment = comment,
        date = date,
        lastSyncDate = lastSyncDate,
    )
}
