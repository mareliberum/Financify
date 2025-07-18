package com.example.yandexsummerschool.di.modules

import android.content.Context
import com.example.yandexsummerschool.data.local.room.dao.AccountDao
import com.example.yandexsummerschool.data.local.room.dao.CategoriesDao
import com.example.yandexsummerschool.data.local.room.dao.PendingTransactionsDao
import com.example.yandexsummerschool.data.local.room.dao.TransactionsDao
import com.example.yandexsummerschool.data.local.room.dataBase.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTransactionsDao(dataBase: AppDataBase): TransactionsDao {
        return dataBase.getTransactionsDao()
    }

    @Provides
    @Singleton
    fun accountDao(dataBase: AppDataBase): AccountDao {
        return dataBase.getAccountDao()
    }

    @Provides
    @Singleton
    fun categoriesDao(dataBase: AppDataBase): CategoriesDao {
        return dataBase.getCategoriesDao()
    }

    @Provides
    @Singleton
    fun providePendingTransactionsDao(dataBase: AppDataBase): PendingTransactionsDao {
        return dataBase.getPendingTransactionsDao()
    }
}
