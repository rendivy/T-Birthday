package ru.yangel.hackathon.wishlist.item.presentation.state

data class WishlistItemEditContent(
    val name: String = "",
    val isNameCorrect: Boolean = true,
    val price: String = "",
    val isPriceCorrect: Boolean = true,
    val link: String = "",
    val priority: String = "",
    val isPriorityCorrect: Boolean = true,
    val comment: String = ""
)