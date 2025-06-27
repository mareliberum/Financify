package com.example.yandexsummerschool.domain.utils

/**
 * Конвертирует дату из формата dd/MM/yyyy в формат yyyy-MM-dd
 * @param input [String] дата в формате dd/MM/yyyy
 * @return [String] дата в формате yyyy-MM-dd"
 */
fun convertDateToIso(input: String): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val date = inputFormat.parse(input)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        ""
    }
}
