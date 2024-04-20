package ru.yangel.hackathon.follows.data.model


import com.google.gson.annotations.SerializedName

data class AffiliateResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)