package ru.yangel.hackathon.wishlist.item.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.yangel.hackathon.wishlist.item.data.repository.WishlistItemRepository

fun provideWishlistItemDomainModule() = module {
    single {
        WishlistItemRepository(get(), androidContext().applicationContext)
    }
}