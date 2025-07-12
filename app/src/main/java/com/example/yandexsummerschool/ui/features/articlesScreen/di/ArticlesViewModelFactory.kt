package com.example.yandexsummerschool.ui.features.articlesScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.ui.features.articlesScreen.ArticlesScreenViewModel
import javax.inject.Inject

class ArticlesViewModelFactory @Inject constructor(
    private val articlesComponent: ArticlesComponent,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticlesScreenViewModel::class.java)) {
            val viewModel =
                ArticlesScreenViewModel(
                    articlesComponent.getArticlesUseCase(),
                )
            articlesComponent.inject(viewModel)
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
