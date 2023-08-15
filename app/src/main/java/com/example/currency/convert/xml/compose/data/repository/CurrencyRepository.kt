package com.example.currency.convert.xml.compose.data.repository

import com.example.currency.convert.xml.compose.common.Resource
import com.example.currency.convert.xml.compose.data.module.CurrencyResponce

interface CurrencyRepository {

    suspend fun getRates(base : String) : Resource<CurrencyResponce>
}