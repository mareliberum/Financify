package com.example.yandexsummerschool.ui.features.expensesScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.di.modules.ViewModelKey
import com.example.yandexsummerschool.ui.features.expensesScreen.ExpensesScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ExpensesViewModelModule {
    @Binds
    fun bindsViewModelFactory(viewModelFactory: ExpensesViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesScreenViewModel::class)
    fun bindsExpensesViewModel(vm: ExpensesScreenViewModel): ViewModel
}
