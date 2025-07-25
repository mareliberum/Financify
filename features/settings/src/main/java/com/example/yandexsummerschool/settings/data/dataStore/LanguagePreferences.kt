package com.example.yandexsummerschool.settings.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.yandexsummerschool.domain.models.AppLocale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object LanguagePreferences {
    private val LOCALE_KEY = stringPreferencesKey("app_locale")
    fun getAppLocale(context: Context): Flow<AppLocale> = context.dataStore.data
        .map { prefs -> AppLocale.fromCode(prefs[LOCALE_KEY] ?: "en") }

    suspend fun setAppLocale(context: Context, locale: AppLocale) {
        context.dataStore.edit { prefs ->
            prefs[LOCALE_KEY] = locale.code
        }
    }
}
