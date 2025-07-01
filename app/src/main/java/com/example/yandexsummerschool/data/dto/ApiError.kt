package com.example.yandexsummerschool.data.dto

/**
 * дата класс хранящий инормацию об ошибке с апи
 * @property message сообщение ошибки
 * @property code код ошибки
 */
data class ApiError(
    val message: String,
    val code: Int? = null,
)
