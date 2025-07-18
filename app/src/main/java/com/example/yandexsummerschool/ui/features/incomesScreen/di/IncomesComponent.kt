package com.example.yandexsummerschool.ui.features.incomesScreen.di

import com.example.yandexsummerschool.di.dependencies.IncomesDependencies
import com.example.yandexsummerschool.ui.features.incomesScreen.IncomesScreenViewModel
import dagger.Component

@IncomesScope
@Component(
    dependencies = [IncomesDependencies::class],
    modules = [IncomesViewModelModule::class],
)
interface IncomesComponent : IncomesDependencies {
    fun inject(viewModel: IncomesScreenViewModel)

    fun getIncomesViewModelFactory(): IncomesViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(dependencies: IncomesDependencies): IncomesComponent
    }
}
