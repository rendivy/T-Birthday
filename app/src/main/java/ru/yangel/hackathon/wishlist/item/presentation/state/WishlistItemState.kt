package ru.yangel.hackathon.wishlist.item.presentation.state

import ru.yangel.hackathon.wishlist.item.data.model.RemoteWishlistItemModel

sealed interface WishlistItemState {
    data object Loading : WishlistItemState
    data class Content(val content: RemoteWishlistItemModel) : WishlistItemState
    data object Error : WishlistItemState
}