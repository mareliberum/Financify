package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.models.TransactionModel

/**
 * Интерфейс репозитория для работы с транзакциями SHMR Finance API
 */
interface TransactionsRepository {
    suspend fun getTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionModel>>

    suspend fun getIncomeTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionModel>>

    suspend fun getExpenseTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionModel>>
}
