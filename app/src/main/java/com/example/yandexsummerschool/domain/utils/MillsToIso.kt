package com.example.yandexsummerschool.domain.utils

/**
 * Форматирует текущее время в милисеккундах в дату ISO-формата yyyy-MM-dd
 * @param millis [Long] текущее время в милисеккундах
 * @return [String] дата в формате yyyy-MM-dd
 */
fun millisToIso(millis: Long): String {
    val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
    format.timeZone = java.util.TimeZone.getTimeZone("UTC")
    return format.format(java.util.Date(millis))
}
