package ru.yangel.hackathon.auth.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yangel.hackathon.auth.data.Constants
import ru.yangel.hackathon.auth.data.api.LoginApi
import ru.yangel.hackathon.auth.data.interceptor.AuthInterceptor
import java.util.concurrent.TimeUnit

private fun provideRegularOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build()

//Не переиспользовать
private fun provideLoginRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(Constants.LOGIN_BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson())).build()

private fun provideLoginApi(): LoginApi = provideLoginRetrofit(provideRegularOkHttpClient()).create(LoginApi::class.java)

fun provideAuthNetworkModule() = module {

    single { provideLoginApi() }

    factory {
        AuthInterceptor(get())
    }

}