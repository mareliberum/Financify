package com.example.yandexsummerschool.settings.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object ThemePreferences {
    private val THEME_KEY = booleanPreferencesKey("dark_theme")

    fun getTheme(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[THEME_KEY] ?: false }

    suspend fun saveTheme(context: Context, isDark: Boolean) {
        context.dataStore.edit { it[THEME_KEY] = isDark }
    }
}
