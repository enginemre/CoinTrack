package com.engin.cointrack.feature.home.data.di

import android.content.Context
import androidx.room.Room
import com.engin.cointrack.core.data.remote.RetrofitFactory
import com.engin.cointrack.feature.home.data.local.CoinDatabase
import com.engin.cointrack.feature.home.data.remote.HomeApi
import com.engin.cointrack.feature.home.data.remote.retrofit.HomeApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeDataModule {

    @Provides
    @Singleton
    fun bindHomeApi(retrofitFactory : RetrofitFactory): HomeApi{
        return HomeApiImpl(retrofitFactory)
    }

    @Provides
    @Singleton
    fun provideCoinDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CoinDatabase::class.java,
        "coin_database"
    ).build()

    @Provides
    @Singleton
    fun provideCoinDao(
      coinDatabase: CoinDatabase
    ) = coinDatabase.coinDao
}