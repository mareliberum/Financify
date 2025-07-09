package com.example.yandexsummerschool

import android.app.Application
import com.example.yandexsummerschool.di.dagger.DaggerAppComponent

class ShmrApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.create()
    }
}
