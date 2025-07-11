package com.example.yandexsummerschool.ui.features.incomesScreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yandexsummerschool.ui.features.incomesScreen.IncomesScreenViewModel
import javax.inject.Inject

class IncomesViewModelFactory @Inject constructor(
    private val incomesComponent: IncomesComponent,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncomesScreenViewModel::class.java)) {
            val viewModel =
                IncomesScreenViewModel(
                    incomesComponent.getIncomesUseCase(),
                    incomesComponent.getUserDelegate(),
                    incomesComponent.getAccountUseCase(),
                )
            incomesComponent.inject(viewModel)
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
