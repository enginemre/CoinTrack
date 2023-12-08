package com.engin.cointrack.core.domain.model

data class Coin(
    val id : String,
    val name : String,
    val symbol : String,
    val imageUrl : String,
    var isFavourite : Boolean = false
)
