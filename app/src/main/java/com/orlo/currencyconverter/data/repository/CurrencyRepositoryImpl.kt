package com.orlo.currencyconverter.data.repository

import com.orlo.currencyconverter.data.local.CurrencyRateDao
import com.orlo.currencyconverter.data.local.entity.toCurrencyRate
import com.orlo.currencyconverter.data.local.entity.toCurrencyRateEntity
import com.orlo.currencyconverter.data.remote.CurrencyApi
import com.orlo.currencyconverter.domain.model.CurrencyRate
import com.orlo.currencyconverter.domain.model.Resources
import com.orlo.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import toCurrencyRates
import java.io.IOError
import java.io.IOException

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
    private val dao: CurrencyRateDao
): CurrencyRepository {
    override fun getCurrencyRateList(): Flow<Resources<List<CurrencyRate>>> = flow {

        val localCurrencyRates = getLocalCurrencyRates()
        emit(Resources.Succes(localCurrencyRates))

        try {
            val newRates = getRemoteCurrencyRates()
            updateLocalCurrencyRates(newRates)
            emit(Resources.Succes(newRates))
        } catch (e: IOException) {
            emit(
                Resources.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = localCurrencyRates
                )
            )
        }
        catch (e: Exception) {
            emit(
                Resources.Error(
                    message = "Oops, something went wrong!",
                    data = localCurrencyRates
                )
            )
        }

    }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        return dao.getAllCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        dao.upsertAll(currencyRates = currencyRates.map { it.toCurrencyRateEntity() })
    }

}