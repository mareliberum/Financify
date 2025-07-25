package com.example.yandexsummerschool.incomesScreen.di


object IncomesDiProvider {
    fun provideFactory(dependencies: IncomesDependencies): IncomesViewModelFactory {

        val incomesComponent = DaggerIncomesComponent.factory().create(dependencies)
        return incomesComponent.getIncomesViewModelFactory()
    }
}
