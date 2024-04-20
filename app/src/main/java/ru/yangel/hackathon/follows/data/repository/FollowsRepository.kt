package ru.yangel.hackathon.follows.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yangel.hackathon.follows.data.api.SearchUsersApiService
import ru.yangel.hackathon.follows.data.model.UserSearchResponse

class FollowsRepository(private val searchUsersApiService: SearchUsersApiService) {

    suspend fun searchUsersByName(username: String): List<UserSearchResponse> {
        return searchUsersApiService.getUsersByName(username)
    }
}