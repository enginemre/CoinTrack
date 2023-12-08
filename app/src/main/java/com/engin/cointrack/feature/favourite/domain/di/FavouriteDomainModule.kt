package com.engin.cointrack.feature.favourite.domain.di

import com.engin.cointrack.feature.favourite.data.usecase.GetAllFavouritesUseCaseImpl
import com.engin.cointrack.feature.favourite.data.usecase.RemoveFavouriteUseCaseImpl
import com.engin.cointrack.feature.favourite.domain.GetAllFavouritesUseCase
import com.engin.cointrack.feature.favourite.domain.RemoveFavouriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavouriteDomainModule {

    @Binds
    fun bindGetAllFavouritesUseCase(getAllFavouritesUseCaseImpl: GetAllFavouritesUseCaseImpl) : GetAllFavouritesUseCase

    @Binds
    fun bindRemoveFavouritesUseCase(removeFavouriteUseCaseImpl: RemoveFavouriteUseCaseImpl): RemoveFavouriteUseCase
}