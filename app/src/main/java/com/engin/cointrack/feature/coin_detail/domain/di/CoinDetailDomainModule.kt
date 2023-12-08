package com.engin.cointrack.feature.coin_detail.domain.di

import com.engin.cointrack.feature.coin_detail.data.usecase.AddFavouriteUseCaseImpl
import com.engin.cointrack.feature.coin_detail.data.usecase.GetCoinDetailUseCaseImpl
import com.engin.cointrack.feature.coin_detail.domain.usecase.AddFavouriteUseCase
import com.engin.cointrack.feature.coin_detail.domain.usecase.GetCoinDetailUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoinDetailDomainModule  {

    @Binds
    fun bindAddFavouriteUseCase(addFavouriteUseCaseImpl: AddFavouriteUseCaseImpl) : AddFavouriteUseCase

    @Binds
    fun bindGetCoinDetailUseCase(coinDetailUseCaseImpl: GetCoinDetailUseCaseImpl) : GetCoinDetailUseCase
}