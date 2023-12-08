package com.engin.cointrack.feature.coin_detail.domain.usecase

import com.engin.cointrack.core.domain.model.Coin
import com.engin.cointrack.feature.coin_detail.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface AddFavouriteUseCase {
    operator fun invoke(coinDetail : CoinDetail) : Flow<Boolean>
}