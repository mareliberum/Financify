package com.example.yandexsummerschool.analysisScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.analysisScreen.AnalysisScreenViewModel
import com.example.yandexsummerschool.features.analysisScreen.di.AnalysisViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.reflect.KClass

@Module
interface AnalysisViewModelModule {
    @Binds
    fun bindsViewModelFactory(factory: AnalysisViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AnalysisScreenViewModel::class)
    fun bindsAnalysisViewModel(vm: AnalysisScreenViewModel): ViewModel
}

@MapKey
@Retention(RUNTIME)
@Target(FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
