package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.CreatedTransactionDomainModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.models.UpdatedTransactionDomainModel

interface PendingTransactionsRepository {
    suspend fun insertPendingTransaction(createdTransactionDomainModel: CreatedTransactionDomainModel)

    suspend fun getPendingTransactions(): Result<List<CreatedTransactionDomainModel>>

    suspend fun sendPendingTransactions()

    suspend fun postPendingTransactions(transaction: CreatedTransactionDomainModel): Result<Boolean>

    suspend fun getPendingUpdates(): Result<List<UpdatedTransactionDomainModel>>

    suspend fun insertPendingUpdate(update: UpdatedTransactionDomainModel)

    suspend fun updatePendingTransactions(update: UpdatedTransactionDomainModel): Result<Boolean>
}
