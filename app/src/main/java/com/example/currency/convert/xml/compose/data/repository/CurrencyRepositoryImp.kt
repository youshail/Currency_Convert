package com.example.currency.convert.xml.compose.data.repository

import com.example.currency.convert.xml.compose.common.Resource
import com.example.currency.convert.xml.compose.data.CurrencyApi
import com.example.currency.convert.xml.compose.data.module.CurrencyResponce
import javax.inject.Inject
import kotlin.Exception

class CurrencyRepositoryImp @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRepository {
    override suspend fun getRates(base: String): Resource<CurrencyResponce> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


}