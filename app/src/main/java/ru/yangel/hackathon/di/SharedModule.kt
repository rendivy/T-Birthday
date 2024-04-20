package ru.yangel.hackathon.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yangel.hackathon.auth.data.interceptor.AuthInterceptor
import ru.yangel.hackathon.wishlist.item.data.api.Constants
import java.util.concurrent.TimeUnit

fun provideAuthOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
    OkHttpClient.Builder().addInterceptor(authInterceptor).connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson())).build()


fun provideNetworkModule() = module {
    single {
        provideAuthOkHttpClient(get())
    }
    single {
        provideRetrofit(get())
    }

}