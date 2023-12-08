package com.engin.cointrack.feature.login.domain.di

import com.engin.cointrack.feature.login.data.LoginUserUseCaseImpl
import com.engin.cointrack.feature.login.domain.LoginUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoginDomainModule {
    @Binds
    fun bindLoginUseCaseImpl(loginUserUseCaseImpl: LoginUserUseCaseImpl) : LoginUserUseCase
}