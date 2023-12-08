package com.engin.cointrack.feature.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.engin.cointrack.feature.home.data.local.entity.CoinEntity

@Dao
interface CoinDao {

    @Update
    suspend fun upsertCoin(coinEntity: CoinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(coins: List<CoinEntity>)

    @Transaction
    suspend fun insertCoinsInTransaction(coins: List<CoinEntity>) {
        insertAllCoins(coins)
    }

    @Query("SELECT * FROM coins")
    suspend fun getAllCoins(): List<CoinEntity>

    @Query("SELECT * FROM coins WHERE name LIKE :namePrefix || '%' OR symbol LIKE :namePrefix || '%'")
    suspend fun getCoinsStartingWith(namePrefix: String): List<CoinEntity>

    @Query("DELETE FROM coins")
    suspend fun deleteAllCoins()

}