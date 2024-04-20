package ru.yangel.hackathon.follows.data.model


import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("affiliateAddress")
    val affiliateAddress: String,
    @SerializedName("affiliateId")
    val affiliateId: String,
    @SerializedName("affiliateName")
    val affiliateName: String,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("commandNames")
    val commandNames: List<String>,
    @SerializedName("email")
    val email: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("onlineStatus")
    val onlineStatus: Boolean,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("username")
    val username: String
)