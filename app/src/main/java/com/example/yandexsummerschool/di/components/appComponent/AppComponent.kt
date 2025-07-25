package com.example.yandexsummerschool.di.components.appComponent

import android.content.Context
import com.example.yandexsummerschool.MainActivity
import com.example.yandexsummerschool.work_manager.SynchronizeWorkManager
import com.example.yandexsummerschool.articlesScreen.di.ArticlesDependencies
import com.example.yandexsummerschool.di.modules.DataBaseModule
import com.example.yandexsummerschool.di.modules.NetworkModule
import com.example.yandexsummerschool.di.modules.RepositoryModule
import com.example.yandexsummerschool.di.modules.ViewModelModule
import com.example.yandexsummerschool.expensesScreen.di.ExpensesDependencies
import com.example.yandexsummerschool.analysisScreen.di.AnalysisDependencies
import com.example.yandexsummerschool.incomesScreen.di.IncomesDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        DataBaseModule::class,
    ],
)
interface AppComponent : ExpensesDependencies, IncomesDependencies, ArticlesDependencies, AnalysisDependencies {
    fun inject(mainActivity: MainActivity)

    fun injectToWorker(synchronizeWorkManager: SynchronizeWorkManager)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}
