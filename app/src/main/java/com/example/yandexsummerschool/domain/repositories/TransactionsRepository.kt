package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.TransactionDomainModel

/**
 * Интерфейс репозитория для работы с транзакциями SHMR Finance API
 */
interface TransactionsRepository {
    suspend fun getTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionDomainModel>>

    suspend fun getIncomeTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionDomainModel>>

    suspend fun getExpenseTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?,
    ): Result<List<TransactionDomainModel>>

    suspend fun postTransaction(
        transaction: TransactionDomainModel,
    ): Result<TransactionDomainModel>

    suspend fun updateTransaction(
        transaction: TransactionDomainModel,
    ): Result<TransactionDomainModel>
}
