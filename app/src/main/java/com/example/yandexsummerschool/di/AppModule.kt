package com.example.yandexsummerschool.di

import com.example.yandexsummerschool.data.repositories.TransactionsRepository
import com.example.yandexsummerschool.data.repositories.TransactionsRepositoryImpl
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger-модуль для предоставления репозитория [TransactionsRepository].
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideShmrFinanceRepository(shmrFinanceApi: ShmrFinanceApi): TransactionsRepository {
        return TransactionsRepositoryImpl(shmrFinanceApi)
    }
}
