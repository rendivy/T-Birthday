package ru.yangel.hackathon.follows.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yangel.hackathon.follows.data.api.SearchUsersApiService
import ru.yangel.hackathon.follows.data.model.AffiliateResponse
import ru.yangel.hackathon.follows.data.model.CommandResponse
import ru.yangel.hackathon.follows.data.model.UserSearchResponse

class FollowsRepository(private val searchUsersApiService: SearchUsersApiService) {

    suspend fun searchUsersByName(username: String): List<UserSearchResponse> {
        return searchUsersApiService.getUsersByName(username)
    }

    suspend fun searchUsersByCommand(command: String): List<CommandResponse> {
        return searchUsersApiService.getCommand(command)
    }

    suspend fun searchUsersByAffiliate(affiliate: String): List<AffiliateResponse> {
        return searchUsersApiService.getAffiliate(affiliate)
    }

    suspend fun searchFollowedUsersByName(): List<UserSearchResponse> {
        return searchUsersApiService.getFollowedUsersByName()
    }

    suspend fun searchFollowedUsersByCommand(): List<CommandResponse> {
        return searchUsersApiService.getFollowedCommand()
    }

    suspend fun searchFollowedUsersByAffiliate(): List<AffiliateResponse> {
        return searchUsersApiService.getFollowedAffiliate()
    }
}