package ru.yangel.hackathon.wishlist.item.presentation

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.yangel.hackathon.wishlist.item.data.model.ImageModel
import ru.yangel.hackathon.wishlist.item.data.model.UpdateWishListItemModel
import ru.yangel.hackathon.wishlist.item.data.model.WishlistItemModel
import ru.yangel.hackathon.wishlist.item.data.repository.WishlistItemRepository
import ru.yangel.hackathon.wishlist.item.presentation.state.WishlistItemEditContent
import ru.yangel.hackathon.wishlist.item.presentation.state.WishlistItemEditState

class WishlistItemEditViewModel(
    private val itemId: String, private val wishlistItemRepository: WishlistItemRepository
) : ViewModel() {

    private val _state =
        mutableStateOf(if (itemId.isNotBlank()) WishlistItemEditState.Loading else WishlistItemEditState.Content)
    val state: State<WishlistItemEditState>
        get() = _state

    private val _successEvents = MutableSharedFlow<Unit>()
    val successEvents = _successEvents.asSharedFlow()

    val existingImages = mutableStateListOf<ImageModel>()

    val deletedPhotoIds = mutableListOf<String>()

    private val _content = mutableStateOf(WishlistItemEditContent())
    val content: State<WishlistItemEditContent>
        get() = _content

    val imageUris = mutableStateListOf<Uri>()

    val canSubmit = derivedStateOf {
        _content.value.name.isNotBlank() && _content.value.priority.isNotBlank() && _content.value.isPriorityCorrect && (_content.value.isPriceCorrect || _content.value.price.isBlank())
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.value = WishlistItemEditState.Error
    }

    init {
        if (itemId.isNotBlank())
            reload()
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
        _state.value = WishlistItemEditState.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val content = _content.value
            if (itemId.isBlank()) {
                wishlistItemRepository.uploadItem(
                    WishlistItemModel(
                        name = content.name,
                        price = content.price.toFloatOrNull(),
                        link = content.link.ifBlank { null },
                        priority = content.priority.toInt(),
                        comment = content.comment.ifBlank { null },
                        photos = imageUris
                    )
                )
            } else {
                wishlistItemRepository.updateItem(
                    UpdateWishListItemModel(
                        id = itemId,
                        name = content.name,
                        price = content.price.toFloatOrNull(),
                        link = content.link.ifBlank { null },
                        priority = content.priority.toInt(),
                        comment = content.comment.ifBlank { null },
                        newPhotos = imageUris,
                        deletedPhotoIds = deletedPhotoIds
                    )
                )
            }
            _successEvents.emit(Unit)
        }
    }

    fun reload() {
        if (_content.value.name.isNotBlank() || itemId.isBlank()) {
            _state.value = WishlistItemEditState.Content
            return
        }
        _state.value = WishlistItemEditState.Loading
        deletedPhotoIds.clear()
        existingImages.clear()
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = wishlistItemRepository.getItem(itemId)
            _content.value = WishlistItemEditContent(
                name = response.name,
                price = response.price?.toString() ?: "",
                link = response.link ?: "",
                priority = response.rating.toString(),
                comment = response.comment ?: ""
            )
            response.photos?.let {
                existingImages.addAll(it)
            }
            _state.value = WishlistItemEditState.Content
        }
    }
}