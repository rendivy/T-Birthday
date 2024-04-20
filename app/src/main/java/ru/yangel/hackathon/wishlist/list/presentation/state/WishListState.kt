package ru.yangel.hackathon.wishlist.list.presentation.state

import ru.yangel.hackathon.wishlist.item.data.model.WishListItemRemoteModel

sealed interface WishListState {

    data object Loading: WishListState

    data object Error: WishListState

    data class Data(val items: List<WishListItemRemoteModel>): WishListState

}