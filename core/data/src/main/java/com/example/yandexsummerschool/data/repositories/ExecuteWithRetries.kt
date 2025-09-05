package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.repositories.transactions.MAX_CONNECTION_RETRIES
import com.example.yandexsummerschool.data.repositories.transactions.RETRY_DELAY
import kotlinx.coroutines.delay
import retrofit2.Response

suspend fun <T> executeWithRetries(
    functionBlock: suspend () -> Response<T>,
): Response<T> {
    // Максимум {MAX_CONNECTION_RETRIES} попытки подключится с интервалом {RETRY_DELAY} сек.
    repeat(MAX_CONNECTION_RETRIES) { attempt ->
        val response = functionBlock()
        if (response.isSuccessful) {
            return response
        } else if (attempt == MAX_CONNECTION_RETRIES - 1) {
            return response
        } else if (response.code() in 500..599 && attempt < MAX_CONNECTION_RETRIES) {
            delay(RETRY_DELAY)
        } else {
            return response
        }
    }
    return functionBlock()
}
