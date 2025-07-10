package com.example.yandexsummerschool.di

import android.content.Context
import com.example.yandexsummerschool.MainActivity
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
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}
