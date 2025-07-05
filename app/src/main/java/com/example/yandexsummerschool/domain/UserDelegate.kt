package com.example.yandexsummerschool.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDelegate @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val prefs: SharedPreferences
        get() = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun saveAccountId(accountId: Int) =
        withContext(Dispatchers.IO) {
            prefs.edit { putInt(KEY_ACCOUNT_ID, accountId) }
        }

    suspend fun getAccountId(): Int? =
        withContext(Dispatchers.IO) {
            val id = prefs.getInt(KEY_ACCOUNT_ID, -1)
            if (id != -1) id else null
        }

    suspend fun clearAccountId() =
        withContext(Dispatchers.IO) {
            prefs.edit { remove(KEY_ACCOUNT_ID) }
        }

    suspend fun saveCurrency(currency: String) =
        withContext(Dispatchers.IO) {
            prefs.edit { putString(KEY_CURRENCY, currency) }
        }

    suspend fun getCurrency(): String? =
        withContext(Dispatchers.IO) {
            prefs.getString(KEY_CURRENCY, null)
        }

    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_ACCOUNT_ID = "account_id"
        private const val KEY_CURRENCY = "currency"
    }
}
