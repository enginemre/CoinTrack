package com.engin.cointrack.feature.home.data.mapper

import com.engin.cointrack.feature.home.data.local.entity.CoinEntity
import com.engin.cointrack.feature.home.data.remote.dto.CoinResponseDto
import com.engin.cointrack.core.domain.model.Coin

fun CoinResponseDto.toCoin()=  Coin(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = "https://assets.coincap.io/assets/icons/${symbol}@2x.png",
        isFavourite = false
    )


fun CoinResponseDto.toCoinEntity() = CoinEntity(
    id = id,
    symbol = symbol,
    name = name,
    imageUrl = "https://assets.coincap.io/assets/icons/${symbol}@2x.png",
    isFavourite = false
)

fun CoinEntity.toCoin() =  Coin(
    id = id,
    symbol = symbol,
    name = name,
    imageUrl = imageUrl,
    isFavourite = isFavourite
)