package com.example.yandexsummerschool.di.components.appComponent

import android.content.Context
import com.example.yandexsummerschool.MainActivity
import com.example.yandexsummerschool.data.SynchronizeWorkManager
import com.example.yandexsummerschool.di.dependencies.ArticlesDependencies
import com.example.yandexsummerschool.di.dependencies.ExpensesDependencies
import com.example.yandexsummerschool.di.dependencies.IncomesDependencies
import com.example.yandexsummerschool.di.modules.DataBaseModule
import com.example.yandexsummerschool.di.modules.NetworkModule
import com.example.yandexsummerschool.di.modules.RepositoryModule
import com.example.yandexsummerschool.di.modules.ViewModelModule
import com.example.yandexsummerschool.ui.features.analysisScreen.di.AnalysisDependencies
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
