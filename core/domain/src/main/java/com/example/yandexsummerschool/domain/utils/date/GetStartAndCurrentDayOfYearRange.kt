package com.example.yandexsummerschool.domain.utils.date

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Возвращает пару значени вида YYYY-MM-DD
 * где первое - начало текущего года
 * второе - текущий день
 */
fun getStartAndCurrentDayOfYearRange(): Pair<String, String> {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Начало года
    val startCal = Calendar.getInstance().apply {
        set(Calendar.MONTH, Calendar.JANUARY)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    // Конец диапазона (текущий день)
    val endCal = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val startDate = formatter.format(startCal.time)
    val endDate = formatter.format(endCal.time)

    return startDate to endDate
}
