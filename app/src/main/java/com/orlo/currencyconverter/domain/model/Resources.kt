package com.orlo.currencyconverter.domain.model

import android.os.Message

sealed class Resources<T> (val data: T? = null, val message: String? = null) {
    class Succes<T>(data: T? = null): Resources<T>(data)
    class Error<T>(message: String, data: T? = null): Resources<T>(data,message)
}