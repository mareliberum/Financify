package com.example.yandexsummerschool.settings.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.settings.ui.SettingsScreenViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.reflect.KClass

@Module
interface SettingsViewModelModule {
    @Binds
    fun bindsViewModelFactory(viewModelFactory: SettingsViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SettingsScreenViewModel::class)
    fun bindsExpensesViewModel(vm: SettingsScreenViewModel): ViewModel
}

@MapKey
@Retention(RUNTIME)
@Target(FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
