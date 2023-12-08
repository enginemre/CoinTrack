package com.engin.cointrack.feature.coin_detail.domain.usecase

import com.engin.cointrack.feature.coin_detail.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface GetCoinDetailUseCase {

    operator fun invoke(id :String) : Flow<CoinDetail>
}