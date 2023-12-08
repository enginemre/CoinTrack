package com.engin.cointrack.feature.coin_detail.data.remote.retrofit

import com.engin.cointrack.feature.coin_detail.data.remote.dto.CoinDetailResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinDetailRetrofitApi {

    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") id : String,
        @Query("localization") localization : Boolean = false,
        @Query("tickers") isTickerEnable : Boolean = false,
        @Query("community_data") isCommunityDataEnable : Boolean = false,
        @Query("developer_data") isDeveloperDataEnable : Boolean = false,
        ) : Response<CoinDetailResponseDto>

}