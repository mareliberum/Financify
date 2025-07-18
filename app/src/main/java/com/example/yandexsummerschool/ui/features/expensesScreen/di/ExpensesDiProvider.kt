package com.example.yandexsummerschool.ui.features.expensesScreen.di

import android.content.Context
import com.example.yandexsummerschool.appComponent

object ExpensesDiProvider {
    fun provideFactory(context: Context): ExpensesViewModelFactory {
        val appComponent = context.appComponent
        val expensesComponent = DaggerExpensesComponent.factory().create(appComponent)
        return expensesComponent.getExpensesViewModelFactory()
    }
}
