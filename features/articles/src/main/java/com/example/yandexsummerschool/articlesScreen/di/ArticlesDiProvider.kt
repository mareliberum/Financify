package com.example.yandexsummerschool.articlesScreen.di

object ArticlesDiProvider {
    fun provideFactory(dependencies: ArticlesDependencies): ArticlesViewModelFactory {
        val articlesComponent = DaggerArticlesComponent.factory().create(dependencies)
        return ArticlesViewModelFactory(articlesComponent)
    }
}
