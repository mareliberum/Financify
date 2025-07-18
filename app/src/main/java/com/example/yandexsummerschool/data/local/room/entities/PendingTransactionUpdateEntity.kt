package com.example.yandexsummerschool.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel

@Entity
data class PendingTransactionUpdateEntity(
    @PrimaryKey val transactionId: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val comment: String? = null,
    val date: String,
)

fun UpdatedTransactionDomainModel.toPendingTransactionUpdateEntity(): PendingTransactionUpdateEntity {
    return PendingTransactionUpdateEntity(
        transactionId,
        accountId,
        categoryId,
        amount,
        comment,
        date,
    )
}

fun PendingTransactionUpdateEntity.toUpdatedTransactionDomainModel(): UpdatedTransactionDomainModel{
    return UpdatedTransactionDomainModel(
        transactionId,
        accountId,
        categoryId,
        amount,
        comment,
        date,
    )
}
