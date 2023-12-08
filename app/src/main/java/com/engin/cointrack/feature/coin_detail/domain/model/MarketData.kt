package com.engin.cointrack.feature.coin_detail.domain.model

data class MarketData(
    val currentPrice : Double,
    val lastUpdated : String,
    val priceChangePercentage24h : Double
)
