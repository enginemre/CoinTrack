package com.engin.cointrack.feature.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val name : String,
    val symbol : String,
    val imageUrl : String,
    val isFavourite : Boolean = false
)
