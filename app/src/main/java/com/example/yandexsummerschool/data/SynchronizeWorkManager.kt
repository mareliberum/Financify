package com.example.yandexsummerschool.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.yandexsummerschool.appComponent
import com.example.yandexsummerschool.data.local.sharedPrefs.SynchronisationTimeDelegate
import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.models.Result.Failure
import com.example.yandexsummerschool.domain.models.Result.Success
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.SendPendingTransactionsUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.SynchronizeTransactionsUseCase
import javax.inject.Inject

/**
 * Загружает с API всю информацию об аккаунте: транзакции, категории, счет и тд
 */
class SynchronizeWorkManager (
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    @Inject
    lateinit var getAccountUseCase: GetAccountUseCase

    @Inject
    lateinit var userDelegate: UserDelegate

    @Inject
    lateinit var syncTimeDelegate: SynchronisationTimeDelegate

    @Inject
    lateinit var synchronizeTransactionsUseCase: SynchronizeTransactionsUseCase

    @Inject
    lateinit var getArticlesUseCase: GetArticlesUseCase

    @Inject
    lateinit var sendPendingTransactionsUseCase: SendPendingTransactionsUseCase

    override suspend fun doWork(): Result {
        context.appComponent.injectToWorker(this)
        val id = userDelegate.getAccountId() ?: getAccountId()
        getArticlesUseCase()
        sendPendingTransactionsUseCase()
        getAccountId()
        if (id != null) {
            val result = synchronizeTransactionsUseCase(id)
            if (result is Success)
                {
                    syncTimeDelegate.saveLastSyncTime()
                }
        }
        return Result.success()
    }

    private suspend fun getAccountId(): Int? {
        when (val account = getAccountUseCase()) {
            is Failure -> {
                return null
            }

            is Success -> {
                val id = account.data.id
                userDelegate.saveAccountId(id)
                return id
            }
        }
    }
}
