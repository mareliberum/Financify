package com.example.yandexsummerschool.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yandexsummerschool.data.local.room.entities.CategorySumResult
import com.example.yandexsummerschool.data.local.room.entities.TransactionEntity

@Dao
interface TransactionsDao {
    @Query("SELECT * FROM TransactionEntity WHERE date >= :startDate AND date <= :endDate")
    fun getAllTransactions(startDate: String, endDate: String): List<TransactionEntity>

    @Query("SELECT * FROM TransactionEntity WHERE date >= :startDate and date <= :endDate AND isIncome==1")
    fun getIncomeTransactions(startDate: String, endDate: String): List<TransactionEntity>

    @Query("SELECT * FROM TransactionEntity WHERE date >= :startDate and date <= :endDate AND isIncome==0")
    fun getExpenseTransactions(startDate: String, endDate: String): List<TransactionEntity>

    @Query("Select * from transactionentity where id = :id limit 1")
    fun getTransactionById(id: Int): TransactionEntity?

    @Insert(
        entity = TransactionEntity::class,
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insertTransaction(transaction: TransactionEntity)

    @Insert(
        entity = TransactionEntity::class,
        onConflict = OnConflictStrategy.REPLACE,
    )
    fun insertAllTransactions(transactions: List<TransactionEntity>)

    @Query("delete from TransactionEntity where id = :id ")
    fun deleteTransaction(id: Int)

    @Query(
        """
        SELECT
            categoryId,
            categoryName,
            emoji,
            SUM(CAST(amount AS REAL)) as totalAmount
        FROM TransactionEntity
        WHERE date >= :startDate AND date <= :endDate AND isIncome = :isIncome
        GROUP BY categoryId, categoryName, emoji
        """,
    )
    fun getSumByCategoryWithFilter(
        startDate: String,
        endDate: String,
        isIncome: Boolean,
    ): List<CategorySumResult>

    @Query("update transactionentity set currency = :currency")
    fun changeCurrency(currency: String)
}
