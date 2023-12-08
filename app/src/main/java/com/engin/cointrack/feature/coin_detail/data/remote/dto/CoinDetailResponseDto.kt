package com.engin.cointrack.feature.coin_detail.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CoinDetailResponseDto(
    @SerializedName("description")
    val description: DescriptionDto,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String? =null,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: ImageDto,
    @SerializedName("market_data")
    val marketData: MarketDataDto,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)