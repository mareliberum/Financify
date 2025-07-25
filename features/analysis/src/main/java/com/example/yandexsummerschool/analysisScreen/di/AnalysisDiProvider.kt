package com.example.yandexsummerschool.analysisScreen.di

import androidx.lifecycle.ViewModelProvider

object AnalysisDiProvider {
    fun provideFactory(dependencies: AnalysisDependencies): ViewModelProvider.Factory {
        val analysisComponent = DaggerAnalysisComponent.factory().create(dependencies)
        return analysisComponent.getAnalysisViewModelFactory()
    }
}
