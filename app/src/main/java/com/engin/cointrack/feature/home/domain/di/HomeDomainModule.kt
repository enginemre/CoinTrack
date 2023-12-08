package com.engin.cointrack.feature.home.domain.di

import com.engin.cointrack.feature.home.data.usecase.GetCoinsByNameAndSlugImpl
import com.engin.cointrack.feature.home.data.usecase.GetCoinsFromDBUseCaseImpl
import com.engin.cointrack.feature.home.data.usecase.GetCoinsUseCaseImpl
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsByNameAndSlug
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsFromDBUseCase
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeDomainModule {

    @Binds
    fun bindGetCoinsUseCase(getCoinsUseCaseImpl: GetCoinsUseCaseImpl): GetCoinsUseCase

    @Binds
    fun bindGetCoinsByNameAndSlug(getCoinsByNameAndSlugImpl: GetCoinsByNameAndSlugImpl) : GetCoinsByNameAndSlug

    @Binds
    fun bindGetCoinsFromDBSlug(getCoinsFromDBUseCaseImpl: GetCoinsFromDBUseCaseImpl) : GetCoinsFromDBUseCase

}