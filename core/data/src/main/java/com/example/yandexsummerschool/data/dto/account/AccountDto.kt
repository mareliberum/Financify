package com.example.yandexsummerschool.data.dto.account

import com.example.yandexsummerschool.domain.models.AccountModel

data class AccountDto(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)

fun AccountDto.toAccountModel(): AccountModel =
    AccountModel(
        id,
        userId,
        name,
        balance,
        currency,
        createdAt,
        updatedAt,
    )
