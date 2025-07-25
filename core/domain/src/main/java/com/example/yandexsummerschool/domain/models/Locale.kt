package com.example.yandexsummerschool.domain.models


enum class AppLocale(val code: String) {
    RUSSIAN("ru"),
    ENGLISH("en");

    companion object {
        fun fromCode(code: String): AppLocale =
            values().find { it.code == code } ?: ENGLISH
    }
}
