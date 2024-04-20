package ru.yangel.hackathon.auth.data.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.yangel.hackathon.auth.data.api.LoginApi

class AuthInterceptor(private val loginApi: LoginApi) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val tokens = runBlocking { loginApi.login() }
        val requestWithHeader =
            chain.request().newBuilder().addHeader("Authorization", "Bearer ${tokens.accessToken}")
                .build()

        return chain.proceed(requestWithHeader)
    }

}