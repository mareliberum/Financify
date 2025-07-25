package com.example.yandexsummerschool.expensesScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.expensesScreen.ExpensesScreenViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.reflect.KClass

@Module
interface ExpensesViewModelModule {
    @Binds
    fun bindsViewModelFactory(viewModelFactory: ExpensesViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesScreenViewModel::class)
    fun bindsExpensesViewModel(vm: ExpensesScreenViewModel): ViewModel
}

@MapKey
@Retention(RUNTIME)
@Target(FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
