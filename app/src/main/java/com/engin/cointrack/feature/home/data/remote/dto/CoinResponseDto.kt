package com.engin.cointrack.feature.home.data.remote.dto


import com.google.gson.annotations.SerializedName


data class CoinResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)