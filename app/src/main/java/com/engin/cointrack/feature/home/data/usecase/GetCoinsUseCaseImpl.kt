package com.engin.cointrack.feature.home.data.usecase

import com.engin.cointrack.feature.home.data.local.CoinDao
import com.engin.cointrack.feature.home.data.local.CoinDatabase
import com.engin.cointrack.feature.home.data.mapper.toCoin
import com.engin.cointrack.feature.home.data.mapper.toCoinEntity
import com.engin.cointrack.feature.home.data.remote.HomeApi
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinsUseCaseImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val coinDao: CoinDao
) : GetCoinsUseCase {
    override fun invoke() = flow {
        val apiResult = homeApi.getCoins()
        coinDao.deleteAllCoins()
        coinDao.insertCoinsInTransaction(apiResult.map { it.toCoinEntity() })
        emit( coinDao.getAllCoins().map { it.toCoin() })
    }.flowOn(Dispatchers.IO)

}