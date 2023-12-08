package com.engin.cointrack.feature.coin_detail.data.di

import com.engin.cointrack.feature.coin_detail.data.remote.CoinDetailApi
import com.engin.cointrack.feature.coin_detail.data.remote.retrofit.CoinDetailApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoinDetailDataModule {

    @Binds
    fun bindCoinDetailApi(coinDetailApiImpl: CoinDetailApiImpl) : CoinDetailApi
}