package com.example.yandexsummerschool.di.dependencies

import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase

interface ExpensesDependencies {
    fun getExpensesUseCase(): GetExpensesUseCase

    fun getAccountUseCase(): GetAccountUseCase

    fun getUserDelegate(): UserDelegate
}
