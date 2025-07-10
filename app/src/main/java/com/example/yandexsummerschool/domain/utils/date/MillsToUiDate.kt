package com.example.yandexsummerschool.domain.utils.date

/**
 * Форматирует текущее время в милисеккундах в дату формата dd/MM/yyyy
 * @param millis текущее время в милисеккундах
 * @return [String] дата в формате dd/MM/yyyy
 */
fun millsToUiDate(millis: Long?): String {
    return millis?.let {
        java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            .format(java.util.Date(it))
    } ?: "—"
}
