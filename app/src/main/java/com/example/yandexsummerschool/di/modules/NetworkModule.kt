package com.example.yandexsummerschool.di.modules

import com.example.yandexsummerschool.BuildConfig
import com.example.yandexsummerschool.data.retrofit.ShmrAccountApi
import com.example.yandexsummerschool.data.retrofit.ShmrArticlesApi
import com.example.yandexsummerschool.data.retrofit.ShmrFinanceApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://shmr-finance.ru/api/v1/"

/**
 * Dagger-модуль для предоставления сетевых зависимостей:
 * - [OkHttpClient]
 * - [Retrofit]
 * - [ShmrFinanceApi]
 */
@Module
object NetworkModule {
    private val token: String
        get() = BuildConfig.TOKEN

    @Provides
    fun providesBaseUrl(): String = BASE_URL

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer $token",
                        )
                        .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideShmrFinanceApi(retrofit: Retrofit): ShmrFinanceApi = retrofit.create(ShmrFinanceApi::class.java)

    @Provides
    @Singleton
    fun providesShmrAccountApi(retrofit: Retrofit): ShmrAccountApi = retrofit.create(ShmrAccountApi::class.java)

    @Provides
    @Singleton
    fun providesShmrArticlesApi(retrofit: Retrofit): ShmrArticlesApi = retrofit.create(ShmrArticlesApi::class.java)
}
