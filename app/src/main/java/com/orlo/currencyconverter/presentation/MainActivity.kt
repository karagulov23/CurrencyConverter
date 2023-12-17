package com.orlo.currencyconverter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orlo.currencyconverter.presentation.main_screen.MainScreen
import com.orlo.currencyconverter.presentation.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                MainScreen()
            }
        }
    }
}

