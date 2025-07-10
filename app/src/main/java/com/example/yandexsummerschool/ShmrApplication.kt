package com.example.yandexsummerschool

import android.app.Application
import android.content.Context
import com.example.yandexsummerschool.di.AppComponent
import com.example.yandexsummerschool.di.DaggerAppComponent

class ShmrApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() =
        when (this) {
            is ShmrApplication -> this.appComponent
            else -> (this.applicationContext as ShmrApplication).appComponent
        }
