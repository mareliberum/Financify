package com.example.yandexsummerschool.domain.utils.date

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * Возвращает ISO-строку начала или конца дня для даты в формате yyyy-MM-dd.
 * @param dateString дата в формате yyyy-MM-dd
 * @param atEndOfDay если true — конец дня (23:59:59.999), иначе — начало (00:00:00.000)
 * @return ISO-строка в формате yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
 */
fun makeFullIsoDate(dateString: String?, atEndOfDay: Boolean = false): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateString) ?: return ""
        val calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                time = date
                if (atEndOfDay) {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                } else {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
            }

        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val result = outputFormat.format(calendar.time)
        result
    } catch (e: Exception) {
        ""
    }
}

/**
 * Возвращает ISO-строку для даты, заданной в миллисекундах.
 * @param millis миллисекунды с начала эпохи
 * @return ISO-строка в формате yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
 */
fun makeFullIsoDate(millis: Long): String {
    return try {
        val calendar =
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = millis
            }
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")
        outputFormat.format(calendar.time)
    } catch (e: Exception) {
        ""
    }
}
