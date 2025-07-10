package com.example.yandexsummerschool.ui.screens.addTransactionScreen

import androidx.lifecycle.ViewModel
import com.example.yandexsummerschool.domain.useCases.transactions.CreateTransactionUseCase
import javax.inject.Inject

class AddTransactionScreenViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
) : ViewModel() {
    fun createTransaction() {
    }
}
