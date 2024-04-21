package ru.yangel.hackathon.login.data

import ru.yangel.hackathon.auth.data.api.LoginApi

class LoginRepository(
    private val tokenLocalStorage: TokenLocalStorage,
    private val loginService: LoginApi
) {

    suspend fun login(username: String, password: String) {
        val tokenResponse = loginService.login(
            username = username,
            password = password
        )
        tokenLocalStorage.saveToken(tokenResponse.accessToken)
    }

}