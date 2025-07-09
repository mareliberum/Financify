package com.example.yandexsummerschool.di.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    // Пример провайдера зависимости на уровне приложения
    @Provides
    @Singleton
    fun provideExampleString(): String = "Hello from Dagger 2!"
} 
