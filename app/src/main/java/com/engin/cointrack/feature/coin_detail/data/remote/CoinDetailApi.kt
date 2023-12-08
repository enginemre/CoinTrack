package com.engin.cointrack.feature.coin_detail.data.remote

import com.engin.cointrack.feature.coin_detail.data.remote.dto.CoinDetailResponseDto

interface CoinDetailApi {
    suspend fun getCoinDetail(id : String) : CoinDetailResponseDto
}