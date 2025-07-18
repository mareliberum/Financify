package com.example.yandexsummerschool.data.local.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.yandexsummerschool.data.local.sharedPrefs.PreferencesConstants.ACCOUNT_PREFS_NAME
import com.example.yandexsummerschool.data.local.sharedPrefs.PreferencesConstants.Keys.KEY_ACCOUNT_ID
import com.example.yandexsummerschool.data.local.sharedPrefs.PreferencesConstants.Keys.KEY_CURRENCY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

// TODO - разделить реализацию и интерфейс
@Singleton
class UserDelegate @Inject constructor(
    private val context: Context,
) : UserAccountIdPrefs, UserCurrencyPrefs {
    private val prefs: SharedPreferences
        get() = context.getSharedPreferences(ACCOUNT_PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun saveAccountId(accountId: Int) =
        withContext(Dispatchers.IO) {
            prefs.edit { putInt(KEY_ACCOUNT_ID, accountId) }
        }

    override suspend fun getAccountId(): Int? =
        withContext(Dispatchers.IO) {
            val id = prefs.getInt(KEY_ACCOUNT_ID, -1)
            if (id != -1) id else null
        }

    suspend fun clearAccountId() =
        withContext(Dispatchers.IO) {
            prefs.edit { remove(KEY_ACCOUNT_ID) }
        }

    override suspend fun saveCurrency(currency: String) =
        withContext(Dispatchers.IO) {
            prefs.edit { putString(KEY_CURRENCY, currency) }
        }

    override suspend fun getCurrency(): String? =
        withContext(Dispatchers.IO) {
            prefs.getString(KEY_CURRENCY, null)
        }
}
