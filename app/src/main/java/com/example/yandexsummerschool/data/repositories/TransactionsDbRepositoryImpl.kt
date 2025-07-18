package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.local.room.dao.TransactionsDao
import com.example.yandexsummerschool.data.local.room.entities.toGroupedByCategoryTransactions
import com.example.yandexsummerschool.data.local.room.entities.toTransactionDomainModel
import com.example.yandexsummerschool.data.local.room.entities.toTransactionEntity
import com.example.yandexsummerschool.domain.models.GroupedByCategoryTransactions
import com.example.yandexsummerschool.domain.models.TransactionDomainModel
import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsDbRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao,
) : TransactionsDbRepository {
    override suspend fun getAllTransactionsByPeriod(startDate: String, endDate: String): List<TransactionDomainModel> {
        return withContext(Dispatchers.IO) {
            dao.getAllTransactions(startDate, endDate).map { it.toTransactionDomainModel() }
        }
    }

    override suspend fun getIncomeTransactionsByPeriod(
        startDate: String,
        endDate: String,
    ): List<TransactionDomainModel> {
        return withContext(Dispatchers.IO) {
            dao.getIncomeTransactions(startDate, endDate).map { it.toTransactionDomainModel() }
        }
    }

    override suspend fun getExpenseTransactionsByPeriod(
        startDate: String,
        endDate: String,
    ): List<TransactionDomainModel> {
        return withContext(Dispatchers.IO) {
            dao.getExpenseTransactions(startDate, endDate).map { it.toTransactionDomainModel() }
        }
    }

    override suspend fun insertTransaction(transactionDomainModel: TransactionDomainModel) {
        withContext(Dispatchers.IO) {
            dao.insertTransaction(transactionDomainModel.toTransactionEntity())
        }
    }

    override suspend fun insertAllTransactions(transactionDomainModels: List<TransactionDomainModel>) {
        withContext(Dispatchers.IO) {
            dao.insertAllTransactions(transactionDomainModels.map { it.toTransactionEntity() })
        }
    }

    override suspend fun getSumByCategory(
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): List<GroupedByCategoryTransactions> {
        return withContext(Dispatchers.IO) {
            dao.getSumByCategoryWithFilter(startDate, endDate, isIncome).map { it.toGroupedByCategoryTransactions() }
        }
    }
}
