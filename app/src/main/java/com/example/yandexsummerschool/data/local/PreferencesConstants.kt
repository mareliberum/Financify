package com.example.yandexsummerschool.data.local

internal object PreferencesConstants {
    const val PREFS_NAME = "app_prefs"

    object Keys {
        const val KEY_ACCOUNT_ID = "account_id"
        const val KEY_CURRENCY = "currency"
    }

    object Defaults {
        const val INVALID_ACCOUNT_ID = -1
    }
}
