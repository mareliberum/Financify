package com.example.yandexsummerschool.settings.data.repository

import android.content.Context
import com.example.yandexsummerschool.domain.repositories.SettingsRepository
import com.example.yandexsummerschool.settings.data.dataStore.ThemePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val context: Context,
) : SettingsRepository {
    override fun isDarkTheme(): Flow<Boolean> =
        ThemePreferences.getTheme(context)

    override suspend fun setDarkTheme(enabled: Boolean) {
        withContext(Dispatchers.IO){
            ThemePreferences.saveTheme(context, enabled)
        }
    }
}
