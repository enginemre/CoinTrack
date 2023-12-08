package com.engin.cointrack.feature.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.engin.cointrack.feature.home.data.local.entity.CoinEntity

@Database(
    entities = [CoinEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CoinDatabase  : RoomDatabase(){
    abstract val coinDao : CoinDao
}