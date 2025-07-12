package com.example.yandexsummerschool.ui.features.incomesScreen

import android.content.Context
import com.example.yandexsummerschool.appComponent
import com.example.yandexsummerschool.ui.features.incomesScreen.di.DaggerIncomesComponent
import com.example.yandexsummerschool.ui.features.incomesScreen.di.IncomesViewModelFactory

object IncomesDiProvider {
    fun provideFactory(context: Context): IncomesViewModelFactory {
        val dependencies = context.appComponent // AppComponent реализует IncomesDependencies
        val incomesComponent = DaggerIncomesComponent.factory().create(dependencies)
        return IncomesViewModelFactory(incomesComponent)
    }
}
