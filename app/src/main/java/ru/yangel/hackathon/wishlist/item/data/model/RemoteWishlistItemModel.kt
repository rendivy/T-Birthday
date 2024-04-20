package ru.yangel.hackathon.wishlist.item.data.model

data class RemoteWishlistItemModel(
    val id: String,
    val name: String,
    val price: Float?,
    val comment: String?,
    val link: String?,
    val rating: Int,
    val isClosed: Boolean,
    val photos: List<ImageModel>?
)
