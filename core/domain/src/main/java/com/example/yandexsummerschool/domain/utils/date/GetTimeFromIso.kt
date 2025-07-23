package com.example.yandexsummerschool.domain.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun getTimeFromIsoDate(isoDate: String): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = isoFormat.parse(isoDate)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormat.format(date ?: "00:00")
    } catch (e: Exception) {
        "00:00"
    }
}
