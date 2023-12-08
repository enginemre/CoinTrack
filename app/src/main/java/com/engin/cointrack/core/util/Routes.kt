package com.engin.cointrack.core.util

sealed interface Destinations {
    data class CoinDetail(val id: String,val name :String) : Destinations
    data object Login : Destinations
    data object Home : Destinations
}