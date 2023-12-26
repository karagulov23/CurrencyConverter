package com.orlo.currencyconverter.presentation.main_screen

import com.orlo.currencyconverter.domain.model.CurrencyRate

data class MainScreenState(
    val fromCurrencyCode: String = "SOM",
    val toCurrencyCode: String = "USD",
    val fromCurrencyValue: String = "0.00",
    val toCurrencyValue: String = "0.00",
    val selection: SelectionState = SelectionState.FROM,
    val currencyRates: Map<String, CurrencyRate> = emptyMap(),
    val error: String? = null
)

enum class SelectionState{
    FROM,
    TO
}