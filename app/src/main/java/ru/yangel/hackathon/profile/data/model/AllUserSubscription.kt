package ru.yangel.hackathon.profile.data.model


import com.google.gson.annotations.SerializedName

data class AllUserSubscription(
    @SerializedName("affiliate")
    val affiliate: Affiliate,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("commands")
    val commands: List<Command>,
    @SerializedName("deliveryDateBefore")
    val deliveryDateBefore: Int,
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