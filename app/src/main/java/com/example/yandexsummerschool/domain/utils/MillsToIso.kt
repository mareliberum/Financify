package com.example.yandexsummerschool.domain.utils

fun millisToIso(millis: Long): String {
	val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
	format.timeZone = java.util.TimeZone.getTimeZone("UTC")
	return format.format(java.util.Date(millis))
}
