package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.data.dto.toArticleModel
import com.example.yandexsummerschool.data.retrofit.ShmrArticlesApi
import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.domain.repositories.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val articlesApi: ShmrArticlesApi,
) : ArticlesRepository {
    override suspend fun getArticles(): Result<List<ArticleModel>> =
        withContext(Dispatchers.IO) {
            val response = executeWIthRetries { articlesApi.getArticles() }
            if (response.isSuccessful) {
                val articlesList = response.body()?.map { it.toArticleModel() } ?: emptyList()
                Result.Success(articlesList)
            } else {
                Result.Failure(Exception(response.message()))
            }
        }
}
