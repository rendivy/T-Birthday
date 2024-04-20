package ru.yangel.hackathon.follows.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.yangel.hackathon.follows.data.model.UserSearchResponse


interface SearchUsersApiService {

    @GET("/user")
    suspend fun getUsersByName(@Query("user_full_name") username: String): List<UserSearchResponse>
}