package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.domain.models.Result

interface ArticlesRepository {
    suspend fun getArticles(): Result<List<ArticleModel>>
}
