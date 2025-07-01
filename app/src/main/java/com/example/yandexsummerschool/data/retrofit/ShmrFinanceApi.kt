package com.example.yandexsummerschool.data.retrofit

import com.example.yandexsummerschool.data.dto.TransactionDto
import retrofit2.Response
import retrofit2.http.GET
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
}
