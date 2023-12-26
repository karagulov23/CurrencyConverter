package com.orlo.currencyconverter.data.local

import androidx.room.Database
import com.orlo.currencyconverter.data.local.entity.CurrencyRateEntity

@Database (entities = [CurrencyRateEntity::class], version = 1)

abstract class CurrencyRateDatabase {

    abstract val currencyRateDao: CurrencyRateDao



}