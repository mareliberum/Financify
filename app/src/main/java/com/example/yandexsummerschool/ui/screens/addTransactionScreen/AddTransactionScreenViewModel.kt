package com.example.yandexsummerschool.ui.screens.addTransactionScreen

import com.example.yandexsummerschool.domain.useCases.transactions.CreateTransactionUseCase
import javax.inject.Inject

// TODO Dagger view model!!!
class AddTransactionScreenViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
) {
    fun createTransaction() {
    }
}
