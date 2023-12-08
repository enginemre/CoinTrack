package com.engin.cointrack.feature.home.data.remote.retrofit

import com.engin.cointrack.core.util.Credentials
import com.engin.cointrack.feature.home.data.remote.dto.CoinResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRetrofitApi {
    @GET("coins/list")
    suspend fun getCoins(@Query("include_platform") currency : Boolean =false) : Response<List<CoinResponseDto>>
}