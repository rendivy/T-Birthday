package ru.yangel.hackathon.wishlist.item.data.repository

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.yangel.hackathon.wishlist.item.data.api.WishlistItemApiService
import ru.yangel.hackathon.wishlist.item.data.model.UpdateWishListItemModel
import ru.yangel.hackathon.wishlist.item.data.model.WishlistItemModel
import java.io.File

class WishlistItemRepository(
    private val apiService: WishlistItemApiService, private val context: Context
) {

    suspend fun uploadItem(wishlistItemModel: WishlistItemModel) {
        var requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", wishlistItemModel.name)
            .addFormDataPart("rating", wishlistItemModel.priority.toString())
        if (wishlistItemModel.price != null) requestBody =
            requestBody.addFormDataPart("price", wishlistItemModel.price.toString())
        if (wishlistItemModel.link != null) requestBody =
            requestBody.addFormDataPart("link", wishlistItemModel.link)
        if (wishlistItemModel.comment != null) requestBody =
            requestBody.addFormDataPart("comment", wishlistItemModel.comment)

        val tempFiles = mutableListOf<File>()

        for (uri in wishlistItemModel.photos) {
            val file = uri.createTempFile()
            requestBody = requestBody.addFormDataPart(
                "photos", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
            )
            tempFiles.add(file)
        }

        apiService.createWishlistItem(requestBody.build())
        for (file in tempFiles) {
            file.delete()
        }
    }

    suspend fun updateItem(updateWishListItemModel: UpdateWishListItemModel) {
        var requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", updateWishListItemModel.name)
            .addFormDataPart("rating", updateWishListItemModel.priority.toString())
        if (updateWishListItemModel.price != null) requestBody =
            requestBody.addFormDataPart("price", updateWishListItemModel.price.toString())
        if (updateWishListItemModel.link != null) requestBody =
            requestBody.addFormDataPart("link", updateWishListItemModel.link)
        if (updateWishListItemModel.comment != null) requestBody =
            requestBody.addFormDataPart("comment", updateWishListItemModel.comment)
        apiService.updateWishlistItem(updateWishListItemModel.id, requestBody.build())

        if (updateWishListItemModel.deletedPhotoIds.isNotEmpty()) {
            var photoDeleteRequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)

            for (id in updateWishListItemModel.deletedPhotoIds) {
                photoDeleteRequestBody = photoDeleteRequestBody.addFormDataPart("photoIds", id)
            }
            apiService.deletePhotos(updateWishListItemModel.id, photoDeleteRequestBody.build())
        }

        if (updateWishListItemModel.newPhotos.isNotEmpty()) {
            var photoUpdateRequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            val tempFiles = mutableListOf<File>()
            for (uri in updateWishListItemModel.newPhotos) {
                val file = uri.createTempFile()
                photoUpdateRequestBody = photoUpdateRequestBody.addFormDataPart(
                    "photos", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
                )
                tempFiles.add(file)
            }

            apiService.uploadPhotos(updateWishListItemModel.id, photoUpdateRequestBody.build())
            for (file in tempFiles) {
                file.delete()
            }
        }
    }

    suspend fun getItem(id: String) = apiService.getWishlistItem(id)

    private fun Uri.createTempFile(): File {
        val fileStream = context.contentResolver.openInputStream(this)
        val fileBytes = fileStream?.readBytes()
        fileStream?.close()

        val outputDir: File = context.cacheDir
        val outputFile = File.createTempFile("prefix", "-suffix", outputDir)
        if (fileBytes != null) {
            outputFile.writeBytes(fileBytes)
        }
        return outputFile
    }

}