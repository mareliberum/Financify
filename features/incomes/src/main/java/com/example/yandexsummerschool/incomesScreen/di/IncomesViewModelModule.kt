package com.example.yandexsummerschool.incomesScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.incomesScreen.IncomesScreenViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.reflect.KClass

@Module
interface IncomesViewModelModule {
    @Binds
    fun bindsViewModelFactory(viewModelFactory: IncomesViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(IncomesScreenViewModel::class)
    fun bindsExpensesViewModel(vm: IncomesScreenViewModel): ViewModel
}

@MapKey
@Retention(RUNTIME)
@Target(FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
