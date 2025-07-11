package com.example.yandexsummerschool.ui.features.expensesScreen

import android.content.Context
import com.example.yandexsummerschool.appComponent
import com.example.yandexsummerschool.ui.features.expensesScreen.di.DaggerExpensesComponent
import com.example.yandexsummerschool.ui.features.expensesScreen.di.ExpensesViewModelFactory

object ExpensesDiProvider {
    fun provideFactory(context: Context): ExpensesViewModelFactory {
        val appComponent = context.appComponent
        val expensesComponent = DaggerExpensesComponent.factory().create(appComponent)
        return ExpensesViewModelFactory(expensesComponent)
    }
}
