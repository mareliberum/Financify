package com.example.yandexsummerschool.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun isDarkTheme(): Flow<Boolean>

    suspend fun setDarkTheme(enabled: Boolean)
}
