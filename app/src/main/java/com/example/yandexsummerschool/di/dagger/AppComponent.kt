package com.example.yandexsummerschool.di.dagger

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    // Здесь будут методы для внедрения зависимостей (inject)
    // Например:
    // fun inject(application: ShmrApplication)
}
