package com.example.yandexsummerschool.articlesScreen.di

import com.example.yandexsummerschool.articlesScreen.ArticlesScreenViewModel
import com.example.yandexsummerschool.features.articlesScreen.di.ArticlesModule
import com.example.yandexsummerschool.features.articlesScreen.di.ArticlesScope
import dagger.Component

@ArticlesScope
@Component(
    dependencies = [ArticlesDependencies::class],
    modules = [ArticlesModule::class],
)
interface ArticlesComponent : ArticlesDependencies {
    fun inject(viewModel: ArticlesScreenViewModel)

    @Component.Factory
    interface Factory {
        fun create(dependencies: ArticlesDependencies): ArticlesComponent
    }
}
