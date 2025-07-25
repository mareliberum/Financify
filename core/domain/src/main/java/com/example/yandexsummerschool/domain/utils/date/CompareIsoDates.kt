package com.example.yandexsummerschool.domain.utils.date

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * - <0 - date1 < date2
 * - '>'0 - date1 > date2
 * - 0  - date1 = date2
 */
fun compareIsoDates(date1: String, date2: String): Int {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    val parsedDate1 = sdf.parse(date1)
    val parsedDate2 = sdf.parse(date2)
    return parsedDate1?.compareTo(parsedDate2) ?: 0
}
