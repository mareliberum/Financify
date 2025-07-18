package com.example.yandexsummerschool.domain.useCases.articles

import com.example.yandexsummerschool.domain.models.ArticleModel
import com.example.yandexsummerschool.domain.models.Result
import com.example.yandexsummerschool.domain.repositories.ArticlesRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository,
) {
    suspend operator fun invoke(): Result<List<ArticleModel>> {
        return repository.getArticles()
    }
}
