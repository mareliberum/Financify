package com.example.yandexsummerschool.di.modules

import com.example.yandexsummerschool.data.repositories.AccountRepositoryImpl
import com.example.yandexsummerschool.data.repositories.ArticlesRepositoryImpl
import com.example.yandexsummerschool.data.repositories.PendingTransactionsRepositoryImpl
import com.example.yandexsummerschool.data.repositories.TransactionsDbRepositoryImpl
import com.example.yandexsummerschool.data.repositories.TransactionsRepositoryImpl
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import com.example.yandexsummerschool.domain.repositories.ArticlesRepository
import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface RepositoryModule {
    @Binds
    @Reusable
    fun bindsTransactionsRepository(transactionRepo: TransactionsRepositoryImpl): TransactionsRepository

    @Binds
    @Reusable
    fun bindsAccountRepository(accountRepo: AccountRepositoryImpl): AccountRepository

    @Binds
    @Reusable
    fun bindsArticlesRepository(articlesRepo: ArticlesRepositoryImpl): ArticlesRepository

    @Binds
    @Reusable
    fun bindsTransactionsDbRepository(transactionsDbRepository: TransactionsDbRepositoryImpl): TransactionsDbRepository

    @Binds
    @Reusable
    fun bindsPendingTransactionsRepository(
        pendingTransactionsRepository: PendingTransactionsRepositoryImpl,
    ): PendingTransactionsRepository
}
