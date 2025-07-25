package com.example.yandexsummerschool.settings.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.example.yandexsummerschool.ui.theme.GreenPrimary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ColorPreferences {
    private val ACCENT_COLOR_KEY = longPreferencesKey("color")

    fun getAccentColor(context: Context): Flow<Long> =
        context.dataStore.data.map { prefs ->
            prefs[ACCENT_COLOR_KEY] ?:  GreenPrimary.value.toLong()
        }

    suspend fun setAccentColor(context: Context, color: Long) {
        context.dataStore.edit { prefs ->
            prefs[ACCENT_COLOR_KEY] = color
        }
    }
}

