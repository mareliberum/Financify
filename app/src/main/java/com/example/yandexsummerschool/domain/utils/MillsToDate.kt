package com.example.yandexsummerschool.domain.utils

fun millsToDate(millis: Long?): String {
	return millis?.let {
		java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
			.format(java.util.Date(it))
	} ?: "—"
}

