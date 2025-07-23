package com.example.yandexsummerschool.articlesScreen.di

import com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase

interface ArticlesDependencies {
    fun getArticlesUseCase(): GetArticlesUseCase
}
