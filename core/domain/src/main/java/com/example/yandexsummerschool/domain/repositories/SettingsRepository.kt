package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.AppLocale
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun isDarkTheme(): Flow<Boolean>

    suspend fun setDarkTheme(enabled: Boolean)

    suspend fun setAccentColor(color: Long)

    fun getAccentColor():Flow<Long>

    suspend fun setSyncFrequency(newFreq: Int)

    fun getSyncFrequencyFlow(): Flow<Int>

    suspend fun setAppLocale(locale: AppLocale)

    fun getAppLocale() : Flow<AppLocale>


}
