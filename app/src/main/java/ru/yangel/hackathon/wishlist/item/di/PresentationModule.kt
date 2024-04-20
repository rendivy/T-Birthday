package ru.yangel.hackathon.wishlist.item.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yangel.hackathon.wishlist.item.presentation.WishlistItemEditViewModel

fun provideWishlistItemPresentationModule() = module {
    viewModel { parametersHolder ->
        WishlistItemEditViewModel(parametersHolder.get(), get())
    }
}