package ru.yangel.hackathon.profile.data.model

import com.google.gson.annotations.SerializedName

data class Id(
    val id: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("photo_url") val photoUrl: String
)
