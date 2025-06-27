package com.example.yandexsummerschool.domain.utils

import android.os.Build
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar

fun getStartOfMonth(): Long {
    val milliseconds =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val now = ZonedDateTime.now(ZoneId.systemDefault())
            val startOfMonth = YearMonth.from(now).atDay(1).atStartOfDay(now.zone)
            startOfMonth.toInstant().toEpochMilli()
        } else {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
    return milliseconds
}
