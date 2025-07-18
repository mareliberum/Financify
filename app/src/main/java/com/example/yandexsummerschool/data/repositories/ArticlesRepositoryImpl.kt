package com.example.yandexsummerschool.data.repositories

import com.example.yandexsummerschool.data.dto.article.toArticleModel
import com.example.yandexsummerschool.data.retrofit.ShmrArticlesApi
import com.example.yandexsummerschool.data.local.room.dao.CategoriesDao
import com.example.yandexsummerschool.data.local.room.entities.toArticleModel
import com.example.yandexsummerschool.data.local.room.entities.toCategoryEntity
import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val articlesApi: ShmrArticlesApi,
    private val dao: CategoriesDao,
) : ArticlesRepository {
    override suspend fun getArticles(): Result<List<ArticleModel>> =
        withContext(Dispatchers.IO) {
            try {
                val response = executeWIthRetries { articlesApi.getArticles() }
                if (response.isSuccessful) {
                    val articlesList = response.body()?.map { it.toArticleModel() } ?: emptyList()
                    dao.insertCategories(articlesList.map { it.toCategoryEntity() })
                    Result.Success(articlesList)
                } else {
                    val categories = dao.getAllCategories()
                    if (categories.isNotEmpty()) {
                        Result.Success(categories.map { it.toArticleModel() })
                    }
                    Result.Failure(Exception(response.message()))
                }
            } catch (e: Exception) {
                val categories = dao.getAllCategories()
                if (categories.isNotEmpty()) {
                    return@withContext Result.Success(categories.map { it.toArticleModel() })
                }
                return@withContext Result.Failure(e)
            }
        }
}
