package com.engin.cointrack.feature.home.domain.usecase

import com.engin.cointrack.core.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface GetCoinsByNameAndSlug {
    operator fun invoke(text : String) : Flow<List<Coin>>
}