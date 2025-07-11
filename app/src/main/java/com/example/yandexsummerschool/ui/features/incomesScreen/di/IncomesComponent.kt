package com.example.yandexsummerschool.ui.features.incomesScreen.di

import com.example.yandexsummerschool.di.dependencies.IncomesDependencies
import com.example.yandexsummerschool.ui.features.incomesScreen.IncomesScreenViewModel
import dagger.Component

@IncomesScope
@Component(
    dependencies = [IncomesDependencies::class],
    modules = [IncomesModule::class],
)
interface IncomesComponent {
    fun inject(viewModel: IncomesScreenViewModel)

    // Методы для получения зависимостей
    fun getIncomesUseCase(): com.example.yandexsummerschool.domain.useCases.incomes.GetIncomesUseCase

    fun getAccountUseCase(): com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase

    fun getUserDelegate(): com.example.yandexsummerschool.data.local.UserDelegate

    @Component.Factory
    interface Factory {
        fun create(dependencies: IncomesDependencies): IncomesComponent
    }
}
