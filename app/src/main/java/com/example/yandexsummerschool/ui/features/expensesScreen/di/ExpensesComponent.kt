package com.example.yandexsummerschool.ui.features.expensesScreen.di

import com.example.yandexsummerschool.data.local.UserDelegate
import com.example.yandexsummerschool.di.components.appComponent.AppComponent
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase
import com.example.yandexsummerschool.ui.features.expensesScreen.ExpensesScreenViewModel
import dagger.Component

@ExpensesScope
@Component(
    dependencies = [AppComponent::class],
)
interface ExpensesComponent {
    fun inject(viewModel: ExpensesScreenViewModel)

    // Методы для получения зависимостей
    fun getExpensesUseCase(): GetExpensesUseCase

    fun getAccountUseCase(): GetAccountUseCase

    fun getUserDelegate(): UserDelegate

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ExpensesComponent
    }
}
