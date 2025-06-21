package com.example.yandexsummerschool.domain.dto

data class ApiError(
	val message: String,
	val code: Int? = null
)