package ru.yangel.hackathon.auth.data.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.yangel.hackathon.auth.data.api.LoginApi
import ru.yangel.hackathon.login.data.TokenLocalStorage

class AuthInterceptor(
    private val loginApi: LoginApi,
    private val tokenLocalStorage: TokenLocalStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val tokens = tokenLocalStorage.getToken()
        val requestWithHeader =
            chain.request().newBuilder().addHeader("Authorization", "Bearer $tokens")
                .build()

        return chain.proceed(requestWithHeader)
    }

}