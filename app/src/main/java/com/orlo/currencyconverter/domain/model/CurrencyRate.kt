package com.orlo.currencyconverter.domain.model

data class CurrencyRate(
    val code: String,
    val name: String,
    val rate: Double
)
