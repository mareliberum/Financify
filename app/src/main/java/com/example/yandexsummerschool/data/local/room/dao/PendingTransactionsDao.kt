package com.example.yandexsummerschool.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yandexsummerschool.data.local.room.entities.PendingTransactionEntity
import com.example.yandexsummerschool.data.local.room.entities.PendingTransactionUpdateEntity

@Dao
interface PendingTransactionsDao {
    @Insert(
        entity = PendingTransactionEntity::class,
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insertPendingTransaction(pendingTransaction: PendingTransactionEntity)

    @Query("SELECT * FROM PendingTransactionEntity")
    fun getAllPendingTransactions(): List<PendingTransactionEntity>

    @Query("SELECT * FROM PendingTransactionEntity where date > :startDate and date < :endDate")
    fun getPendingTransactions(startDate: String, endDate:String): List<PendingTransactionEntity>


    @Query("Delete from PendingTransactionEntity where id = :id")
    fun deletePendingTransaction(id: Int)


    @Insert(
        entity = PendingTransactionUpdateEntity::class,
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insertPendingUpdates(pendingTransaction: PendingTransactionUpdateEntity)

    @Query("SELECT * FROM PendingTransactionUpdateEntity")
    fun getPendingUpdates(): List<PendingTransactionUpdateEntity>

    @Query("Delete from PendingTransactionUpdateEntity where transactionId = :id")
    fun deletePendingUpdates(id: Int)

}
