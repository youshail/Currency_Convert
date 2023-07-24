package com.example.currency.convert.xml.compose.data.module



data class CurrencyResponce(
    val base: String,
    val date: String,
    val motd: Motd,
    val rates: Rates,
    val success: Boolean
)