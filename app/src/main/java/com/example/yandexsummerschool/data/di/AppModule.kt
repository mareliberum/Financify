package com.example.yandexsummerschool.data.di

import com.example.yandexsummerschool.data.repositories.ShmrFinanceRepository
import com.example.yandexsummerschool.data.repositories.ShmrFinanceRepositoryImpl
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	fun provideShmrFinanceRepository(shmrFinanceApi: ShmrFinanceApi): ShmrFinanceRepository{
		return ShmrFinanceRepositoryImpl(shmrFinanceApi)
	}


}