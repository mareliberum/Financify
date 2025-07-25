package com.example.yandexsummerschool.expensesScreen.di

import com.example.yandexsummerschool.expensesScreen.ExpensesScreenViewModel
import dagger.Component

@ExpensesScope
@Component(
    dependencies = [ExpensesDependencies::class],
    modules = [ExpensesViewModelModule::class],
)
interface ExpensesComponent : ExpensesDependencies {
    fun inject(viewModel: ExpensesScreenViewModel)

    fun getExpensesViewModelFactory(): ExpensesViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(dependencies: ExpensesDependencies): ExpensesComponent
    }
}
