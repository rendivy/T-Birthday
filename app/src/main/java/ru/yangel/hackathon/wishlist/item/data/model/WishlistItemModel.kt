package ru.yangel.hackathon.wishlist.item.data.model

import android.net.Uri

data class WishlistItemModel(
    val name: String,
    val price: Float?,
    val link: String?,
    val priority: Int,
    val comment: String?,
    val photos: List<Uri>
)
