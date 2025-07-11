package com.example.yandexsummerschool.di.components.appComponent

import android.content.Context
import com.example.yandexsummerschool.MainActivity
import com.example.yandexsummerschool.di.dependencies.ExpensesDependensies
import com.example.yandexsummerschool.di.dependencies.IncomesDependencies
import com.example.yandexsummerschool.di.dependencies.ArticlesDependencies
import com.example.yandexsummerschool.di.modules.NetworkModule
import com.example.yandexsummerschool.di.modules.RepositoryModule
import com.example.yandexsummerschool.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
    ],
)
interface AppComponent : ExpensesDependensies, IncomesDependencies, ArticlesDependencies {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}
