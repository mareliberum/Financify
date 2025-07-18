package com.example.yandexsummerschool.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel

@Entity
data class PendingTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val comment: String? = null,
    val date: String,
)

fun CreatedTransactionDomainModel.toPendingTransactionEntity(): PendingTransactionEntity {
    return PendingTransactionEntity(
        id = 0,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        comment = comment,
        date = date,
    )
}

fun PendingTransactionEntity.toCreatedTransactionDomainModel(): CreatedTransactionDomainModel {
    return CreatedTransactionDomainModel(
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        comment = comment,
        date = date,
    )
}


