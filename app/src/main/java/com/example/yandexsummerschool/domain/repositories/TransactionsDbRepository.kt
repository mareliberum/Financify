package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.GroupedByCategoryTransactions
import com.example.yandexsummerschool.domain.models.TransactionDomainModel

interface TransactionsDbRepository {
    suspend fun getAllTransactionsByPeriod(startDate: String, endDate: String): List<TransactionDomainModel>

    suspend fun getIncomeTransactionsByPeriod(startDate: String, endDate: String): List<TransactionDomainModel>

    suspend fun getExpenseTransactionsByPeriod(startDate: String, endDate: String): List<TransactionDomainModel>

    suspend fun insertTransaction(transactionDomainModel: TransactionDomainModel)

    suspend fun insertAllTransactions(transactionDomainModels: List<TransactionDomainModel>)

    suspend fun getSumByCategory(
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): List<GroupedByCategoryTransactions>

    suspend fun deleteTransactionById(id: Int)

    suspend fun changeCurrency(currency: String)
}
