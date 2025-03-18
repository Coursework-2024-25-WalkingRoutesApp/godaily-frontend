package ru.hse.coursework.godaily.core.di

import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.data.network.FakeApiService
import ru.hse.coursework.godaily.core.security.JwtManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttp(jwtManager: JwtManager): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(JWTInterceptor(jwtManager))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    //if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                    //}
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, okHttp: Lazy<Call.Factory>): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.example.com/")
            .callFactory { okHttp.get().newCall(it) }
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return FakeApiService()
        //return retrofit.create(ApiService::class.java)
    }
}