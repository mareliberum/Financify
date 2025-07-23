package com.example.yandexsummerschool.expensesScreen.di


object ExpensesDiProvider {
    fun provideFactory(expensesDependencies: ExpensesDependencies): ExpensesViewModelFactory {

        val expensesComponent = DaggerExpensesComponent.factory().create(expensesDependencies)
        return expensesComponent.getExpensesViewModelFactory()
    }
}
