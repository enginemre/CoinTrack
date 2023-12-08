package com.engin.cointrack.feature.coin_detail.domain.model

data class CoinDetail(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val description: String,
    val hashingAlgorithm: String,
    val marketData: MarketData
) {
    constructor() : this("", "", "", "", "", "", MarketData(0.0, "", 0.0))
}
