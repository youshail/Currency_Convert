package com.example.currency.convert.xml.compose.data

import com.example.currency.convert.xml.compose.data.module.CurrencyResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/latest")
    suspend fun getRates(
        @Query("base") base : String
    ): Response<CurrencyResponce>
}