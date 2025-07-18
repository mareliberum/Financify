package com.example.yandexsummerschool.ui.features.analysisScreen.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.appComponent

object AnalysisDiProvider {
    fun provideFactory(context: Context): ViewModelProvider.Factory {
        val appComponent = context.appComponent
        val analysisComponent = DaggerAnalysisComponent.factory().create(appComponent)
        return analysisComponent.getAnalysisViewModelFactory()
    }
}
