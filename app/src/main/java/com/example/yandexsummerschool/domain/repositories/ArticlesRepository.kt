package com.example.yandexsummerschool.domain.repositories

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.models.ArticleModel

interface ArticlesRepository {
    suspend fun getArticles(): Result<List<ArticleModel>>
}
