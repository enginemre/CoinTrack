package com.engin.cointrack.feature.home.data.usecase

import com.engin.cointrack.feature.home.data.local.CoinDao
import com.engin.cointrack.feature.home.data.mapper.toCoin
import com.engin.cointrack.feature.home.domain.usecase.GetCoinsByNameAndSlug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinsByNameAndSlugImpl @Inject constructor(
    private val coinDao: CoinDao
) : GetCoinsByNameAndSlug {
    override fun invoke(text: String) = flow {
        val result = coinDao.getCoinsStartingWith(text)
        emit(result.map { it.toCoin() })
    }.flowOn(Dispatchers.IO)
}