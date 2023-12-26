package com.orlo.currencyconverter.data.local.entity

import com.orlo.currencyconverter.domain.model.CurrencyRate

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {
    return CurrencyRate(
        code = code,
        name = name,
        rate = rate
    )
}
fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity {
    return CurrencyRateEntity(
        code = code,
        name = name,
        rate = rate
    )
}