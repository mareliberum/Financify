package com.example.yandexsummerschool.data.local.sharedPrefs

internal object PreferencesConstants {
    const val ACCOUNT_PREFS_NAME = "app_prefs"
    const val SYNC_PREFS_NAME = "SYNC_PREFS_NAME"

    object Keys {
        const val KEY_ACCOUNT_ID = "account_id"
        const val KEY_CURRENCY = "currency"

        const val LAST_SYNC_TIME = "last_sync_time"
    }

    object Defaults {
        const val INVALID_ACCOUNT_ID = -1
    }
}
