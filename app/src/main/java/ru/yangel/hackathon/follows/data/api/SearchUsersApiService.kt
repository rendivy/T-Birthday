package ru.yangel.hackathon.follows.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.yangel.hackathon.follows.data.model.AffiliateResponse
import ru.yangel.hackathon.follows.data.model.CommandResponse
import ru.yangel.hackathon.follows.data.model.UserSearchResponse


interface SearchUsersApiService {

    @GET("/user")
    suspend fun getUsersByName(@Query("user_full_name") username: String): List<UserSearchResponse>


    @GET("/command")
    suspend fun getCommand(@Query("command_name") command: String): List<CommandResponse>

    @GET("/affiliate")
    suspend fun getAffiliate(@Query("affiliate_name") affiliate: String): List<AffiliateResponse>

    @GET("/api/v1/subscriptions/person/all")
    suspend fun getFollowedUsersByName(): List<UserSearchResponse>

    @GET("/api/v1/subscriptions/command/all")
    suspend fun getFollowedCommand(): List<CommandResponse>

    @GET("/api/v1/subscriptions/affiliate/all")
    suspend fun getFollowedAffiliate(): List<AffiliateResponse>
}