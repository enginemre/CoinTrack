package com.engin.cointrack.feature.home.domain.usecase

import com.engin.cointrack.core.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface GetCoinsFromDBUseCase {

    operator fun invoke() : Flow<List<Coin>>
}