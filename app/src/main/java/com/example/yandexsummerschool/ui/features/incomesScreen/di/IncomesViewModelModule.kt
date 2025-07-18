package com.example.yandexsummerschool.ui.features.incomesScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.di.modules.ViewModelKey
import com.example.yandexsummerschool.ui.features.incomesScreen.IncomesScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface IncomesViewModelModule {
    @Binds
    fun bindsViewModelFactory(viewModelFactory: IncomesViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(IncomesScreenViewModel::class)
    fun bindsExpensesViewModel(vm: IncomesScreenViewModel): ViewModel
}
