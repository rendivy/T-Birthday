package ru.yangel.hackathon.auth.data.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.yangel.hackathon.auth.data.model.TokenResponse

//Данный запрос должен лежать отдельно, т.к. у эндпоинта логина другой порт (другой BASE_URL)
interface LoginApi {

    @FormUrlEncoded
    @POST("realms/hits-project/protocol/openid-connect/token")
    suspend fun login(
        @Field("client_id") clientId: String = "users-app",
        @Field("client_secret") clientSecret: String = "12345678",
        @Field("grant_type") grantType: String = "password",
        @Field("username") username: String = "Stazzz2",
        @Field("password") password: String = "password"
    ): TokenResponse

}