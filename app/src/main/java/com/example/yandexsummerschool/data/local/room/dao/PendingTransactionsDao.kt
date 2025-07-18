package com.example.yandexsummerschool.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yandexsummerschool.data.local.room.entities.PendingTransactionEntity

@Dao
interface PendingTransactionsDao {
    @Insert(
        entity = PendingTransactionEntity::class,
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insertPendingTransaction(pendingTransaction: PendingTransactionEntity)

    @Query("SELECT * FROM PendingTransactionEntity")
    fun getPendingTransactions(): List<PendingTransactionEntity>

    @Query("Delete from PendingTransactionEntity where id = :id")
    fun deletePendingTransaction(id: Int)
}
