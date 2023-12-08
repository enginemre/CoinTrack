package com.engin.cointrack.feature.favourite.domain

import com.engin.cointrack.core.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface RemoveFavouriteUseCase {

    operator fun invoke(coin: Coin) : Flow<Boolean>
}