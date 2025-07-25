package com.example.yandexsummerschool.data.local.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.yandexsummerschool.data.local.sharedPrefs.PreferencesConstants.Keys.LAST_SYNC_TIME
import com.example.yandexsummerschool.data.local.sharedPrefs.PreferencesConstants.SYNC_PREFS_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SynchronisationTimeDelegate @Inject constructor(
    private val context: Context,
) {
    private val prefs: SharedPreferences
        get() = context.getSharedPreferences(SYNC_PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun saveLastSyncTime() {
        withContext(Dispatchers.IO) {
            prefs.edit {
                putLong(LAST_SYNC_TIME, System.currentTimeMillis()).apply()
            }
        }
    }

    suspend fun getLastSyncTime(): Long? {
        return withContext(Dispatchers.IO) {
            val time = prefs.getLong(LAST_SYNC_TIME, 0L)
            if (time != 0L) {
                return@withContext time
            } else {
                return@withContext null
            }
        }
    }
}
