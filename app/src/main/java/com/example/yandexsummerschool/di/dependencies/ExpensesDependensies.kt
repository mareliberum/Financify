package com.example.yandexsummerschool.di.dependencies

import com.example.yandexsummerschool.data.local.UserDelegate
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase

interface ExpensesDependensies {
    fun getExpensesUseCase(): GetExpensesUseCase

    fun getAccountUseCase(): GetAccountUseCase

    fun getUserDelegate(): UserDelegate
}
