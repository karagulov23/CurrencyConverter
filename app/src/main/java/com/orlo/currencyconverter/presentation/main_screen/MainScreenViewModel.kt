package com.orlo.currencyconverter.presentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlo.currencyconverter.domain.model.Resources
import com.orlo.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MainScreenViewModel(private val repository: CurrencyRepository) : ViewModel() {

    var state by mutableStateOf(MainScreenState())

    init {
        getCurrencyRatesList()
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.BottomSheetItemClicked -> {
                state = state.copy(selection = SelectionState.FROM)
            }

            MainScreenEvent.FromCurrencySelect -> {
                state = state.copy(selection = SelectionState.TO)
            }

            is MainScreenEvent.NumberButtonClicked -> {
                state = state.copy(
                    fromCurrencyCode = state.fromCurrencyCode,
                    fromCurrencyValue = state.fromCurrencyValue,
                    toCurrencyCode = state.toCurrencyCode,
                    toCurrencyValue = state.toCurrencyValue
                )
            }
            MainScreenEvent.ToCurrencySelect -> {

            }
            MainScreenEvent.SwapIconClicked -> TODO()
        }
    }

    private fun getCurrencyRatesList() {
        viewModelScope.launch {
            repository
                .getCurrencyRateList()
                .collectLatest { result ->
                    when (result) {
                        is Resources.Error -> state =
                            state.copy(currencyRates = result.data?.associateBy { it.code }
                                ?: emptyMap(), error = null)

                        is Resources.Succes -> state =
                            state.copy(currencyRates = result.data?.associateBy { it.code }
                                ?: emptyMap(), error = result.message)
                    }
                }
        }
    }

    private fun updateCurrencyValue(value: String) {
        val currentCurrencyValue =
            when(state.selection) {
                SelectionState.FROM -> state.fromCurrencyValue
                SelectionState.TO -> state.toCurrencyValue
            }

        val fromCurrencyRate = state.currencyRates[state.fromCurrencyValue]?.rate ?: 0.0
        val toCurrencyRate = state.currencyRates[state.toCurrencyCode]?.rate ?: 0.0

        val updateCurrencyValue =
            when(value) {
                "C" -> "0.00"
                else -> if (currentCurrencyValue == "0.00") value else currentCurrencyValue + value
            }

        val numberFormat = DecimalFormat("#.00")

        when(state.selection) {
            SelectionState.FROM -> {
                val fromValue = updateCurrencyValue.toDoubleOrNull() ?: 0.0
                val toValue = fromValue / fromCurrencyRate * toCurrencyRate
                state = state.copy(
                    fromCurrencyValue = updateCurrencyValue,
                    toCurrencyValue = numberFormat.format(toValue)
                )
            }
            SelectionState.TO -> {
                val fromValue = updateCurrencyValue.toDoubleOrNull() ?: 0.0
                val toValue = fromValue / fromCurrencyRate * toCurrencyRate
                state = state.copy(
                    fromCurrencyValue = updateCurrencyValue,
                    toCurrencyValue = numberFormat.format(toValue)
                )
            }
        }

    }

}