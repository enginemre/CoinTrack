package com.engin.cointrack.feature.home.data.remote.retrofit

import com.engin.cointrack.core.data.remote.RetrofitFactory
import com.engin.cointrack.core.util.Credentials
import com.engin.cointrack.core.util.Exceptions
import com.engin.cointrack.feature.home.data.remote.HomeApi
import javax.inject.Inject

class HomeApiImpl @Inject constructor(
   retrofitFactory: RetrofitFactory
) : HomeApi {

    private val api = retrofitFactory.create<HomeRetrofitApi>()
    override suspend fun getCoins() =
        try {
            val result = api.getCoins()
            if (!result.isSuccessful)
                throw Exceptions.NetworkException(result.message())
            if (result.body() == null)
                throw Exceptions.NetworkException("Response body is null")
            if (result.code().toString() == Credentials.LIMIT_ERROR_CODE)
                throw Exceptions.NetworkException("Reached Limit")
            result.body()!!
        }
        catch (e: Exception) {
            throw e
        }

}