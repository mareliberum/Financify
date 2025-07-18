package com.example.yandexsummerschool.data.local.room.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yandexsummerschool.data.local.room.dao.AccountDao
import com.example.yandexsummerschool.data.local.room.dao.CategoriesDao
import com.example.yandexsummerschool.data.local.room.dao.PendingTransactionsDao
import com.example.yandexsummerschool.data.local.room.dao.TransactionsDao
import com.example.yandexsummerschool.data.local.room.entities.AccountEntity
import com.example.yandexsummerschool.data.local.room.entities.CategoryEntity
import com.example.yandexsummerschool.data.local.room.entities.PendingTransactionEntity
import com.example.yandexsummerschool.data.local.room.entities.PendingTransactionUpdateEntity
import com.example.yandexsummerschool.data.local.room.entities.TransactionEntity

private const val DB_NAME = "APP_DATABASE"

@Database(
    entities = [
        TransactionEntity::class,
        AccountEntity::class,
        CategoryEntity::class,
        PendingTransactionEntity::class,
        PendingTransactionUpdateEntity::class,
    ],
    version = 8,
    exportSchema = false,
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getTransactionsDao(): TransactionsDao

    abstract fun getAccountDao(): AccountDao

    abstract fun getCategoriesDao(): CategoriesDao

    abstract fun getPendingTransactionsDao(): PendingTransactionsDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            AppDataBase::class.java,
                            DB_NAME,
                        ).fallbackToDestructiveMigration(true).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
