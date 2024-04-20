package ru.yangel.hackathon.wishlist.item.data.model

import android.net.Uri

data class UpdateWishListItemModel(
    val id: String,
    val name: String,
    val price: Float?,
    val link: String?,
    val priority: Int,
    val comment: String?,
    val newPhotos: List<Uri>,
    val deletedPhotoIds: List<String>
)
