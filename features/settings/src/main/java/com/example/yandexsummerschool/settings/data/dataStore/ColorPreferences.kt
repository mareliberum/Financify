package com.example.yandexsummerschool.settings.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ColorPreferences {
    private val ACCENT_COLOR_KEY = intPreferencesKey("accent_color")

    fun getAccentColor(context: Context): Flow<Int> =
        context.dataStore.data.map { prefs ->
            prefs[ACCENT_COLOR_KEY] ?: 0
        }

    suspend fun setAccentColor(context: Context, color: Int) {
        context.dataStore.edit { prefs ->
            prefs[ACCENT_COLOR_KEY] = color
        }
    }
}

