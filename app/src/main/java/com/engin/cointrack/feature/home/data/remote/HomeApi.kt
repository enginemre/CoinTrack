package com.engin.cointrack.feature.home.data.remote

import com.engin.cointrack.feature.home.data.remote.dto.CoinResponseDto

interface HomeApi {
    suspend fun getCoins(): List<CoinResponseDto>
}