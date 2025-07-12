package com.example.yandexsummerschool.di.dependencies

import com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase

interface ArticlesDependencies {
    fun getArticlesUseCase(): GetArticlesUseCase
}
