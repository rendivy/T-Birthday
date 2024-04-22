package ru.yangel.hackathon.follows.data.model


import com.google.gson.annotations.SerializedName

data class CommandResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)