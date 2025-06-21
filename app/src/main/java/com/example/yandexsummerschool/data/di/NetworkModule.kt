package com.example.yandexsummerschool.data.di


import com.example.yandexsummerschool.BuildConfig
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	private val token: String
		get() = BuildConfig.TOKEN

	@Provides
	fun providesBaseUrl(): String = "https://shmr-finance.ru/api/v1/"

	@Provides
	fun provideOkHttpClient(): OkHttpClient {
		return OkHttpClient.Builder()
			.addInterceptor { chain ->
				val request = chain.request().newBuilder()
					.addHeader(
						"Authorization",
						"Bearer $token",
					)
					.build()
				chain.proceed(request)
			}
			.build()
	}

	@Provides
	@Singleton
	fun provideRetrofit(
		baseUrl: String,
		client: OkHttpClient
	): Retrofit = Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl(baseUrl)
		.client(client)
		.build()

	@Provides
	@Singleton
	fun provideShmrFinanceApi(retrofit: Retrofit): ShmrFinanceApi =
		retrofit.create(ShmrFinanceApi::class.java)
}
