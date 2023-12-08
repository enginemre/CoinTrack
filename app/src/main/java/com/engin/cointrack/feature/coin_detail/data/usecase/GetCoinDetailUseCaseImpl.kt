package com.engin.cointrack.feature.coin_detail.data.usecase

import com.engin.cointrack.feature.coin_detail.data.mapper.toCoinDetail
import com.engin.cointrack.feature.coin_detail.data.remote.CoinDetailApi
import com.engin.cointrack.feature.coin_detail.domain.usecase.GetCoinDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinDetailUseCaseImpl @Inject constructor(
    private val coinDetailApi: CoinDetailApi
): GetCoinDetailUseCase {
    override fun invoke(id: String) = flow{
        val result =  coinDetailApi.getCoinDetail(id)
        emit(result.toCoinDetail())
    }.flowOn(Dispatchers.IO)
}