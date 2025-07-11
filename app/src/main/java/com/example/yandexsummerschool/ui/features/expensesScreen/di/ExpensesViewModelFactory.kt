package com.example.yandexsummerschool.ui.features.expensesScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.ui.features.expensesScreen.ExpensesScreenViewModel
import javax.inject.Inject

class ExpensesViewModelFactory @Inject constructor(
    private val expensesComponent: ExpensesComponent,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesScreenViewModel::class.java)) {
            // Используем методы expensesComponent для получения зависимостей
            val viewModel =
                ExpensesScreenViewModel(
                    expensesComponent.getExpensesUseCase(),
                    expensesComponent.getAccountUseCase(),
                    expensesComponent.getUserDelegate(),
                )
            expensesComponent.inject(viewModel)
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
