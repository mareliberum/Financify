package com.example.yandexsummerschool.ui.features.articlesScreen.di

import com.example.yandexsummerschool.di.dependencies.ArticlesDependencies
import com.example.yandexsummerschool.ui.features.articlesScreen.ArticlesScreenViewModel
import dagger.Component

@ArticlesScope
@Component(
    dependencies = [ArticlesDependencies::class],
    modules = [ArticlesModule::class],
)
interface ArticlesComponent {
    fun inject(viewModel: ArticlesScreenViewModel)

    // Методы для получения зависимостей
    fun getArticlesUseCase(): com.example.yandexsummerschool.domain.useCases.articles.GetArticlesUseCase

    @Component.Factory
    interface Factory {
        fun create(dependencies: ArticlesDependencies): ArticlesComponent
    }
}
