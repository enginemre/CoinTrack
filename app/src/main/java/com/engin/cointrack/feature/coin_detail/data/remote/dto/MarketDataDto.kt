package com.engin.cointrack.feature.coin_detail.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MarketDataDto(
    @SerializedName("current_price")
    val currentPrice: CurrentPriceDto,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double
)