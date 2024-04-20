package ru.yangel.hackathon.wishlist.item.data.api

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.yangel.hackathon.wishlist.item.data.model.RemoteWishlistItemModel

interface WishlistItemApiService {

    @POST("wishlist/item")
    suspend fun createWishlistItem(@Body body: RequestBody)

    @GET("wishlist/item/{itemId}")
    suspend fun getWishlistItem(@Path("itemId") itemId: String): RemoteWishlistItemModel

    @PUT("wishlist/item/{itemId}")
    suspend fun updateWishlistItem(@Path("itemId") itemId: String, @Body body: RequestBody)

    @POST("wishlist/item/{itemId}/photo")
    suspend fun uploadPhotos(@Path("itemId") itemId: String, @Body body: RequestBody)

    @DELETE("wishlist/item/{itemId}/photo")
    suspend fun deletePhotos(@Path("itemId") itemId: String, @Body body: RequestBody)
}