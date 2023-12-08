package com.engin.cointrack.feature.coin_detail.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("large")
    val large: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("thumb")
    val thumb: String
)