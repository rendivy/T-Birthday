package ru.yangel.hackathon.wishlist.item.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yangel.hackathon.wishlist.item.data.repository.WishlistItemRepository
import ru.yangel.hackathon.wishlist.item.presentation.state.WishlistItemState

class WishlistItemViewModel(private val itemId: String, private val repository: WishlistItemRepository) :
    ViewModel() {

    private val _state = mutableStateOf<WishlistItemState>(WishlistItemState.Loading)
    val state get() = _state

    private val _coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = WishlistItemState.Error
    }

    init {
        reload()
    }

    fun reload() {
        _state.value = WishlistItemState.Loading
        viewModelScope.launch(Dispatchers.IO + _coroutineExceptionHandler) {
            val item = repository.getItem(itemId)
            _state.value = WishlistItemState.Content(item)
        }
    }

    fun markAsBought() {

    }

}
