package com.example.yandexsummerschool.settings.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@SettingsScope
@Component(
    modules = [
        SettingsViewModelModule::class,
        SettingsRepositoryModule::class,
    ]
)
interface SettingsComponent {
    fun getSettingsViewModelFactory(): SettingsViewModelFactory


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): SettingsComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsScope
