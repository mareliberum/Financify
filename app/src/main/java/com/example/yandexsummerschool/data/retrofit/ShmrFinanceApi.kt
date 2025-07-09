package com.example.yandexsummerschool.data.retrofit

import com.example.yandexsummerschool.data.dto.transactions.PostTransactionResponseDto
import com.example.yandexsummerschool.data.dto.transactions.TransactionDto
import com.example.yandexsummerschool.data.dto.transactions.TransactionRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Api интерфейс для работы с SHMR Finance API
 *
 * Все методы возвращают [Response]
 *
 */
interface ShmrFinanceApi {
    @GET("transactions/{id}")
    suspend fun getTransaction(
        @Path("id") id: Int,
    ): Response<TransactionDto>

    /**
     * @param accountId id аккаунта
     *@param startDate string($date) Начальная дата периода (YYYY-MM-DD).
     *Если не указана, используется начало текущего месяца.
     *@param endDate string($date) Конечная дата периода (YYYY-MM-DD).
     *  Если не указана, используется конец текущего месяца.
     */
    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactions(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
    ): Response<List<TransactionDto>>

    @POST("transactions")
    suspend fun postTransaction(
        @Body postTransactionRequestDto: TransactionRequestDto,
    ): Response<PostTransactionResponseDto>

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body updateTransactionRequestDto: TransactionRequestDto,
    ): Response<TransactionDto>
}
