package com.example.yandexsummerschool

import android.app.Application
import android.content.Context
import com.example.yandexsummerschool.di.components.appComponent.AppComponent
import com.example.yandexsummerschool.di.components.appComponent.DaggerAppComponent
import com.yariksoffice.lingver.Lingver
import com.yariksoffice.lingver.store.PreferenceLocaleStore
import java.util.Locale

class ShmrApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        val store = PreferenceLocaleStore(this, Locale("en"))
        Lingver.init(this, store)
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() =
        when (this) {
            is ShmrApplication -> this.appComponent
            else -> (this.applicationContext as ShmrApplication).appComponent
        }
