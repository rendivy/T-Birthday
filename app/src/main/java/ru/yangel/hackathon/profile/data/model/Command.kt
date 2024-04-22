package ru.yangel.hackathon.profile.data.model


import com.google.gson.annotations.SerializedName

data class Command(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)