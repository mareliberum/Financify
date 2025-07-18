package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.Result

interface PendingTransactionsRepository {
    suspend fun insertPendingTransaction(createdTransactionDomainModel: CreatedTransactionDomainModel)

    suspend fun getPendingTransactions(): Result<List<CreatedTransactionDomainModel>>

    suspend fun sendPendingTransactions()

    suspend fun postPendingTransactions(transaction: CreatedTransactionDomainModel): Result<Boolean>
}
