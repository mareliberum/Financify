package com.example.yandexsummerschool.di.modules

import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.data.repositories.account.AccountRepositoryImpl
import com.example.yandexsummerschool.data.repositories.articles.ArticlesRepositoryImpl
import com.example.yandexsummerschool.data.repositories.transactions.PendingTransactionsRepositoryImpl
import com.example.yandexsummerschool.data.repositories.transactions.TransactionsDbRepositoryImpl
import com.example.yandexsummerschool.data.repositories.transactions.TransactionsRepositoryImpl
import com.example.yandexsummerschool.domain.repositories.AccountRepository
import com.example.yandexsummerschool.domain.repositories.ArticlesRepository
import com.example.yandexsummerschool.domain.repositories.PendingTransactionsRepository
import com.example.yandexsummerschool.domain.repositories.TransactionsDbRepository
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import com.example.yandexsummerschool.domain.repositories.UserCurrencyPrefs
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

    @Binds
    @Reusable
    fun bindsUserCurrencyPrefs(userDelegate: UserDelegate): UserCurrencyPrefs
}
