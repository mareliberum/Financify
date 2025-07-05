package com.example.yandexsummerschool.data.retrofit

import com.example.yandexsummerschool.data.dto.ArticleDto
import retrofit2.Response
import retrofit2.http.GET

interface ShmrArticlesApi {
    @GET("categories")
    suspend fun getArticles(): Response<List<ArticleDto>>
}
