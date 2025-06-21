package com.example.yandexsummerschool.domain.utils

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