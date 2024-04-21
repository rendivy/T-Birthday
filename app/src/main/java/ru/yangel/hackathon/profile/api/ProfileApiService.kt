package ru.yangel.hackathon.profile.api


import okhttp3.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.yangel.hackathon.profile.data.model.AllUserSubscription
import ru.yangel.hackathon.profile.data.model.UserDetailsResponse


interface ProfileApiService {

    @GET("user/profile/{userId}")
    suspend fun getProfile(@Path("userId") userId: String): UserDetailsResponse

    @GET("/api/v1/subscriptions/person/all")
    suspend fun getAllSubscriptions(): List<AllUserSubscription>

    @POST("/api/v1/subscriptions/person/{userId}")
    suspend fun subscribe(@Path("userId") userId: String)

    @DELETE("/api/v1/subscriptions/person/{userId}")
    suspend fun unsubscribe(@Path("userId") userId: String)

    @GET("/inventor")
    suspend fun getAiAnswers(@Query("text") promt: String): Response


}