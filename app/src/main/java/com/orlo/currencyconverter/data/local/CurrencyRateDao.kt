package com.orlo.currencyconverter.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.orlo.currencyconverter.data.local.entity.CurrencyRateEntity
@Dao
interface CurrencyRateDao {
    @Upsert
    suspend fun upsertAll(currencyRates: List<CurrencyRateEntity>)
    @Query("Select * From currencyrateentity")
    suspend fun getAllCurrencyRates(): List<CurrencyRateEntity>
}