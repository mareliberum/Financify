package com.example.yandexsummerschool.domain.utils

import java.text.SimpleDateFormat

object DateFormatter {
	fun formatDate(isoDate: String): String {
		return try {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
				val parsed = java.time.OffsetDateTime.parse(isoDate)
				parsed.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			} else {
				// Старый способ: просто взять первые 10 символов
				val trimmed = isoDate.take(10) // yyyy-MM-dd
				val inputFormat = SimpleDateFormat("yyyy-MM-dd")
				val outputFormat = SimpleDateFormat("dd/MM/yyyy")
				val date = inputFormat.parse(trimmed)
				outputFormat.format(date!!)
			}
		} catch (e: Exception) {
			isoDate
		}
	}
}

