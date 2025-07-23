package com.example.yandexsummerschool.analysisScreen.di

import com.example.yandexsummerschool.data.local.sharedPrefs.UserDelegate
import com.example.yandexsummerschool.domain.useCases.account.GetAccountUseCase
import com.example.yandexsummerschool.domain.useCases.expenses.GetExpensesUseCase
import com.example.yandexsummerschool.domain.useCases.incomes.GetIncomesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.GroupTransactionsByCategoriesUseCase
import com.example.yandexsummerschool.domain.useCases.transactions.local.get.GetSumByCategoryUseCase

interface AnalysisDependencies {
    fun getSumByCategoryUseCase(): GetSumByCategoryUseCase

    fun getExpensesUseCase(): GetExpensesUseCase

    fun getIncomesUseCase(): GetIncomesUseCase

    fun groupTransactionsByCategoriesUseCase(): GroupTransactionsByCategoriesUseCase

    fun userDelegate(): UserDelegate

    fun getAccountUseCase(): GetAccountUseCase
}
