package ru.yangel.hackathon.wishlist.list.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yangel.hackathon.wishlist.item.data.repository.WishlistItemRepository
import ru.yangel.hackathon.wishlist.list.presentation.state.WishListState

class WishListViewModel(
    private val userId: String = "", private val repository: WishlistItemRepository
) : ViewModel() {

    private val _state = mutableStateOf<WishListState>(WishListState.Loading)
    val state get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = WishListState.Error
    }

    init {
        loadList()
    }

    fun loadList() {
        _state.value = WishListState.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val items = if (userId.isBlank()) repository.getMyWishlist() else { repository.getWishlist(userId) }
            _state.value = WishListState.Data(items)
        }
    }

}