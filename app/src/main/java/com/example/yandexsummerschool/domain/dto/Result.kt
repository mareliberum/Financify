package com.example.yandexsummerschool.domain.dto

sealed class Result<out T> {
	data class Success<out T>(val data: T) : Result<T>()
	data class Failure<out T>(val exception: Throwable) : Result<T>()
}