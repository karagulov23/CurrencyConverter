package com.orlo.currencyconverter.domain.repository

import com.orlo.currencyconverter.domain.model.CurrencyRate
import com.orlo.currencyconverter.domain.model.Resources
import kotlinx.coroutines.flow.Flow


interface CurrencyRepository {

    fun getCurrencyRateList(): Flow<Resources<List<CurrencyRate>>>

}