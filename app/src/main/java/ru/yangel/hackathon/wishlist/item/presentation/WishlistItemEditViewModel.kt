package ru.yangel.hackathon.wishlist.item.presentation

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.yangel.hackathon.wishlist.item.presentation.state.WishlistItemEditContent
import ru.yangel.hackathon.wishlist.item.presentation.state.WishlistItemEditState

class WishlistItemEditViewModel(itemId: String) : ViewModel() {

    private val _state =
        mutableStateOf(if (itemId.isNotBlank()) WishlistItemEditState.Loading else WishlistItemEditState.Content)
    val state: State<WishlistItemEditState>
        get() = _state

    private val _content = mutableStateOf(WishlistItemEditContent())
    val content: State<WishlistItemEditContent>
        get() = _content

    val imageUris = mutableStateListOf<Uri>()

    val canSubmit = derivedStateOf {
        _content.value.name.isNotBlank() && _content.value.priority.isNotBlank() && _content.value.isPriorityCorrect && (_content.value.isPriceCorrect || _content.value.price.isBlank())
    }

    fun setName(name: String) {
        _content.value = _content.value.copy(
            name = name, isNameCorrect = name.isNotBlank()
        )
    }

    fun setPrice(price: String) {
        val isCorrect = if (price.isNotBlank()) {
            val priceFloat = price.toFloatOrNull()
            priceFloat != null && priceFloat > 0
        } else true
        _content.value = _content.value.copy(
            price = price, isPriceCorrect = isCorrect
        )
    }

    fun setLink(link: String) {
        _content.value = _content.value.copy(
            link = link
        )
    }

    fun setPriority(priority: String) {
        val priorityInt = priority.toIntOrNull()
        val isCorrect = priorityInt != null && priorityInt >= 1 && priorityInt <= 3
        _content.value = _content.value.copy(
            priority = priority, isPriorityCorrect = isCorrect
        )
    }

    fun setComment(comment: String) {
        _content.value = _content.value.copy(
            comment = comment
        )
    }

    fun submit() {

    }

    fun reload() {

    }
}