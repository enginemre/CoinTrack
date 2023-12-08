package com.engin.cointrack.feature.coin_detail.data.remote.dto


import com.google.gson.annotations.SerializedName

data class DescriptionDto(
    @SerializedName("en")
    val en: String
)