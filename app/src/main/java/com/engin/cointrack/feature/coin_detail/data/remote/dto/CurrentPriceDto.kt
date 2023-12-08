package com.engin.cointrack.feature.coin_detail.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CurrentPriceDto(
    @SerializedName("try")
    val tryX: Double,
    @SerializedName("usd")
    val usd: Double
)