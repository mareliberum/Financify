package com.example.yandexsummerschool.di.dagger

import com.example.yandexsummerschool.data.repositories.AccountRepositoryImpl
import com.example.yandexsummerschool.data.repositories.ArticlesRepositoryImpl
import com.example.yandexsummerschool.data.repositories.TransactionsRepositoryImpl
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import com.example.yandexsummerschool.domain.repositories.ArticlesRepository
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindsTransactionsRepository(transactionRepo: TransactionsRepositoryImpl): TransactionsRepository

    @Binds
    fun bindsAccountRepository(accountRepo: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindsArticlesRepository(articlesRepo: ArticlesRepositoryImpl): ArticlesRepository
}
