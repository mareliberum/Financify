package com.example.yandexsummerschool.data.retrofit

import com.example.yandexsummerschool.data.dto.account.AccountDto
import com.example.yandexsummerschool.data.dto.account.AccountUpdateRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

// TODO javadoc
interface ShmrAccountApi {
    @GET("accounts")
    suspend fun getAccount(): Response<List<AccountDto>>

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body updateRequest: AccountUpdateRequestDto,
    ): Response<AccountDto>
}
