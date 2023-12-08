package com.engin.cointrack.feature.coin_detail.data.remote.retrofit

import com.engin.cointrack.core.data.remote.RetrofitFactory
import com.engin.cointrack.core.util.Credentials
import com.engin.cointrack.core.util.Exceptions
import com.engin.cointrack.feature.coin_detail.data.remote.CoinDetailApi
import javax.inject.Inject

class CoinDetailApiImpl @Inject constructor(
    retrofitFactory: RetrofitFactory
) : CoinDetailApi{

    private val api = retrofitFactory.create<CoinDetailRetrofitApi>()
    override suspend fun getCoinDetail(id: String) =
        try {
            val result = api.getCoinDetail(id)
            if (!result.isSuccessful)
                throw Exceptions.NetworkException("${result.code()} error")
            if (result.body() == null)
                throw Exceptions.NetworkException("Response body is null")
            if (result.code().toString() == Credentials.LIMIT_ERROR_CODE)
                throw Exceptions.NetworkException("Reached Limit")
            result.body()!!
        }
        catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

}