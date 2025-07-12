package com.example.yandexsummerschool.ui.features.articlesScreen

import android.content.Context
import com.example.yandexsummerschool.appComponent
import com.example.yandexsummerschool.ui.features.articlesScreen.di.DaggerArticlesComponent
import com.example.yandexsummerschool.ui.features.articlesScreen.di.ArticlesViewModelFactory

object ArticlesDiProvider {
    fun provideFactory(context: Context): ArticlesViewModelFactory {
        val dependencies = context.appComponent // AppComponent реализует ArticlesDependencies
        val articlesComponent = DaggerArticlesComponent.factory().create(dependencies)
        return ArticlesViewModelFactory(articlesComponent)
    }
}
