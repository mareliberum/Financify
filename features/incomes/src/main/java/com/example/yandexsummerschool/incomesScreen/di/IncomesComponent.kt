package com.example.yandexsummerschool.incomesScreen.di


import com.example.yandexsummerschool.incomesScreen.IncomesScreenViewModel
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
