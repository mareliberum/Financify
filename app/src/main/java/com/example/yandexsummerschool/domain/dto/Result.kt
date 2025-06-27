package com.example.yandexsummerschool.domain.dto

/**
 * Обёртка для результата выполнения операции: успех или ошибка.
 */
sealed class Result<out T> {
    /**
     * Успешный результат с данными [data].
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Ошибка выполнения с исключением [exception].
     */
    data class Failure<out T>(val exception: Throwable) : Result<T>()
}
