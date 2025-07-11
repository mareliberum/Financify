package com.example.yandexsummerschool.ui.common

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

// TODO вынести текст в стринг ресурсы
object ErrorMessageResolver {
    fun resolve(throwable: Throwable): String {
        return when (throwable) {
            is SocketTimeoutException -> "Время ожидания ответа от сервера истекло. Попробуйте позже."
            is UnknownHostException, is IOException -> {
                "Нет подключения к интернету. Проверьте соединение и попробуйте снова."
            }

            is HttpException ->
                when (throwable.code()) {
                    500, 502, 503, 504 -> "Сервер временно недоступен. Попробуйте позже."
                    401, 403 -> "Ошибка авторизации. Пожалуйста, войдите заново."
                    404 -> "Данные не найдены."
                    else -> "Ошибка сервера: ${throwable.code()}"
                }
            else -> throwable.message ?: "Произошла неизвестная ошибка"
        }
    }
}
