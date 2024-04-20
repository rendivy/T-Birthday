package ru.yangel.hackathon.profile.data.model


import com.google.gson.annotations.SerializedName

data class Affiliate(
    @SerializedName("address")
    val address: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)