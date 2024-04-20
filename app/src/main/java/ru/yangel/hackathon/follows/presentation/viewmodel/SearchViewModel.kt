package ru.yangel.hackathon.follows.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


class SearchViewModel : ViewModel() {

    val state = MutableStateFlow("")


    fun onStateChange(newValue: String) {
        state.value = newValue
    }
}