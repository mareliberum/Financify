package com.example.yandexsummerschool.settings.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SyncFrequencyPreferences {
    private val SYNC_FREQUENCY_KEY = intPreferencesKey("sync_frequency_minutes")
    private const val DEFAULT_FREQUENCY = 60 // по умолчанию 60 минут

    fun getSyncFrequencyFlow(context: Context): Flow<Int> =
        context.dataStore.data.map { prefs ->
            prefs[SYNC_FREQUENCY_KEY] ?: DEFAULT_FREQUENCY
        }

    suspend fun saveSyncFrequency(context: Context, frequency: Int) {
        context.dataStore.edit { prefs ->
            prefs[SYNC_FREQUENCY_KEY] = frequency
        }
    }
}
