package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.domain.dto.Result
import com.example.yandexsummerschool.domain.models.TransactionModel

/**
 * Предоставляет интерфейс репозитория для работы с SHMR Finance API
 */
interface ShmrFinanceRepository {
    suspend fun getCategories()

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
