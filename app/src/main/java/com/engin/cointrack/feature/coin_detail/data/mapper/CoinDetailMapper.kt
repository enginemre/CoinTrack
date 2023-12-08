package com.engin.cointrack.feature.coin_detail.data.mapper

import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.feature.coin_detail.data.remote.dto.CoinDetailResponseDto
import com.engin.cointrack.feature.coin_detail.data.remote.dto.MarketDataDto
import com.engin.cointrack.feature.coin_detail.domain.model.CoinDetail
import com.engin.cointrack.feature.coin_detail.domain.model.MarketData

fun CoinDetailResponseDto.toCoinDetail() = CoinDetail(
    id = this.id,
    name = this.name,
    symbol= this.symbol,
    imageUrl = this.image.small,
    description = this.description.en,
    hashingAlgorithm = this.hashingAlgorithm.orEmpty(),
    marketData = this.marketData.toMarketData()
)

fun MarketDataDto.toMarketData() = MarketData(
    currentPrice = this.currentPrice.usd,
    lastUpdated = this.lastUpdated,
    priceChangePercentage24h = this.priceChangePercentage24h
)

fun CoinDetail.toCoin() = Coin(
    id, name, symbol, imageUrl,isFavourite = false
)