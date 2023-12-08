package com.engin.cointrack.core.data.remote

import com.engin.cointrack.core.util.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitFactory @Inject constructor(
    val okHttpClient: OkHttpClient,
    val httpLoggingInterceptor: HttpLoggingInterceptor
){
    inline fun <reified T> create() : T {
        return Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(T::class.java)
    }
}