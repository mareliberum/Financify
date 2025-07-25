package com.example.yandexsummerschool.domain.utils.date

/**
 * Конвертирует дату из формата dd/MM/yyyy в формат yyyy-MM-dd
 * @param input [String] дата в формате dd/MM/yyyy
 * @return [String] дата в формате yyyy-MM-dd"
 */
fun convertUiDateToIso(input: String): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val date = inputFormat.parse(input)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        ""
    }
}

/**
 * Преобразует дату и время из форматов dd/MM/yyyy и HH:mm в строку ISO 8601 вида "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" (например, "2025-07-10T18:56:33.877Z").
 *
 * @param date строка даты в формате dd/MM/yyyy (например, "10/07/2025")
 * @param time строка времени в формате HH:mm (например, "18:56")
 * @return строка в формате ISO 8601 (например, "2025-07-10T18:56:33.877Z")
 */
fun convertUiDateToIso(date: String, time: String): String {
    return try {
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
        val dateObj = formatter.parse("$date $time")
        val isoFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.getDefault())
        isoFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
        isoFormat.format(dateObj!!)
    } catch (e: Exception) {
        ""
    }
}
