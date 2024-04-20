package ru.yangel.hackathon.wishlist.item.data.model

data class WishListItemRemoteModel(
    val id: String,
    val name: String,
    val price: Float?,
    val rating: Int,
    val isClosed: Boolean,
    val mainPhoto: ImageModel?
)
