package com.example.yandexsummerschool.di

import com.example.yandexsummerschool.data.repositories.AccountRepositoryImpl
import com.example.yandexsummerschool.data.repositories.ArticlesRepositoryImpl
import com.example.yandexsummerschool.data.repositories.TransactionsRepositoryImpl
import com.example.yandexsummerschool.data.retrofit.ShmrAccountApi
import com.example.yandexsummerschool.data.retrofit.ShmrArticlesApi
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import com.example.yandexsummerschool.domain.repositories.ArticlesRepository
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger-модуль для предоставления репозиториев
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideShmrFinanceRepository(shmrFinanceApi: ShmrFinanceApi): TransactionsRepository {
        return TransactionsRepositoryImpl(shmrFinanceApi)
    }

    @Provides
    fun provideAccountRepository(accountApi: ShmrAccountApi): AccountRepository {
        return AccountRepositoryImpl(accountApi)
    }

    @Provides
    fun provideArticlesRepository(articlesApi: ShmrArticlesApi): ArticlesRepository {
        return ArticlesRepositoryImpl(articlesApi)
    }
}
