package com.example.yandexsummerschool

import android.app.Application
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.yandexsummerschool.di.components.appComponent.AppComponent
import com.example.yandexsummerschool.di.components.appComponent.DaggerAppComponent
import com.example.yandexsummerschool.work_manager.SynchronizeWorkManager
import java.util.concurrent.TimeUnit

class ShmrApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        val workerRequest =
            PeriodicWorkRequestBuilder<SynchronizeWorkManager>(1, TimeUnit.HOURS)
                .build()

        WorkManager.getInstance(this.applicationContext).enqueueUniquePeriodicWork(
            PERIODICAL_SYNC_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            workerRequest,
        )
    }
}

val Context.appComponent: AppComponent
    get() =
        when (this) {
            is ShmrApplication -> this.appComponent
            else -> (this.applicationContext as ShmrApplication).appComponent
        }

const val PERIODICAL_SYNC_WORK = "PERIODICAL_SYNC_WORK"
