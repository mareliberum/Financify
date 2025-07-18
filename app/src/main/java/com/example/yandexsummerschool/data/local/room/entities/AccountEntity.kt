package com.example.yandexsummerschool.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yandexsummerschool.domain.models.AccountModel

@Entity
data class AccountEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)

fun AccountEntity.toAccountModel(): AccountModel {
    return AccountModel(
        id,
        userId,
        name,
        balance,
        currency,
        createdAt,
        updatedAt,
    )
}

fun AccountModel.toAccountEntity(): AccountEntity {
    return AccountEntity(
        id,
        userId,
        name,
        balance,
        currency,
        createdAt,
        updatedAt,
    )
}
