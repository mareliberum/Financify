package com.example.yandexsummerschool.data.retrofit

import com.example.yandexsummerschool.data.dto.ApiError
import com.google.gson.Gson
import okhttp3.ResponseBody

/**
 * Возвращает сообщение и код ошибки из тела ответа
 */
object ErrorParser {
    fun parseError(errorBody: ResponseBody?): ApiError {
        return try {
            errorBody?.string()?.let {
                Gson().fromJson(it, ApiError::class.java)
            } ?: ApiError("Unknown error")
        } catch (e: Exception) {
            ApiError("Failed to parse error: ${e.message}")
        }
    }
}
