package com.example.yandexsummerschool.settings.di

import com.example.yandexsummerschool.settings.data.repository.SettingsRepositoryImpl
import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface SettingsRepositoryModule {

    @Binds
    @Reusable
    fun bindsSettingsRepository(settingsRepo: SettingsRepositoryImpl): SettingsRepository

}
