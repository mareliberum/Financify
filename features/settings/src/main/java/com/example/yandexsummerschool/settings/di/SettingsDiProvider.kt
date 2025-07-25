package com.example.yandexsummerschool.settings.di

import android.content.Context

object SettingsDiProvider {
    fun provideFactory(context: Context): SettingsViewModelFactory {

        val settingsComponent = DaggerSettingsComponent.factory().create(context)
        return settingsComponent.getSettingsViewModelFactory()
    }
}
