package com.engin.cointrack.feature.home.data.usecase

import com.engin.cointrack.feature.home.data.local.CoinDao
import com.engin.cointrack.feature.home.data.mapper.toCoin
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsFromDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinsFromDBUseCaseImpl @Inject constructor(
    private val coinDao: CoinDao
) : GetCoinsFromDBUseCase {
    override fun invoke()= flow {
        emit(coinDao.getAllCoins().map { it.toCoin() })
    }.flowOn(Dispatchers.IO)
}