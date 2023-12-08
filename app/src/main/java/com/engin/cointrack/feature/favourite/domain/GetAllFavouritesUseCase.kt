package com.engin.cointrack.feature.favourite.domain

import com.engin.cointrack.core.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface GetAllFavouritesUseCase {
    operator fun invoke() : Flow<List<Coin>>
}