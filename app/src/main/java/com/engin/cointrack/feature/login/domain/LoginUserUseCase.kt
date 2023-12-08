package com.engin.cointrack.feature.login.domain

import kotlinx.coroutines.flow.Flow

interface LoginUserUseCase {
    operator fun invoke(email: String, password: String) : Flow<Boolean>
}