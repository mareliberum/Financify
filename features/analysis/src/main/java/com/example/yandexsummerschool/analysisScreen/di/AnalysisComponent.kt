package com.example.yandexsummerschool.analysisScreen.di

import androidx.lifecycle.ViewModelProvider
import dagger.Component
import javax.inject.Scope

@AnalysisScope
@Component(
    modules = [AnalysisViewModelModule::class],
    dependencies = [AnalysisDependencies::class],
)
interface AnalysisComponent : AnalysisDependencies {
    fun getAnalysisViewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(dependencies: AnalysisDependencies): AnalysisComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AnalysisScope
