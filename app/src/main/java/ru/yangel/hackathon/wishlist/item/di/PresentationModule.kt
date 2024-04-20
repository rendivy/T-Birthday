package ru.yangel.hackathon.wishlist.item.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yangel.hackathon.wishlist.item.presentation.WishlistItemEditViewModel
import ru.yangel.hackathon.wishlist.item.presentation.WishlistItemViewModel

fun provideWishlistItemPresentationModule() = module {
    viewModel { parametersHolder ->
        WishlistItemEditViewModel(parametersHolder.get(), get())
    }
    viewModel { parametersHolder ->
        WishlistItemViewModel(parametersHolder.get(), get())
    }
}