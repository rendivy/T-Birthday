package ru.yangel.hackathon.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yangel.hackathon.auth.data.interceptor.AuthInterceptor
import ru.yangel.hackathon.calendar.data.serializer.LocalDateDeserializer
import ru.yangel.hackathon.wishlist.item.data.api.Constants
import java.time.LocalDate
import java.util.concurrent.TimeUnit

private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }


fun provideGson(): Gson = GsonBuilder()
    .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer)
    .create()

fun provideAuthOkHttpClient(
    authInterceptor: AuthInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit =
    Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()


fun provideNetworkModule() = module {
    single {
        provideAuthOkHttpClient(get(), get())
    }
    single {
        provideRetrofit(get(), get())
    }
    single {
        provideGson()
    }
    single {
        provideLoggingInterceptor()
    }

}