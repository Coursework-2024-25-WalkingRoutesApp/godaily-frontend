package ru.hse.coursework.godaily.core.di

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.hse.coursework.godaily.core.data.network.ApiService
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
    fun provideMapper(): ObjectMapper {
        return ObjectMapper()
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
    fun provideRetrofit(json: Json, okHttp: Lazy<Call.Factory>, mapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.65:8080/")
            .callFactory { okHttp.get().newCall(it) }
            .addConverterFactory(
                JacksonConverterFactory.create(mapper)
                //TODO удалить сеттингс с хардкодом пути
                //TODO json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        //return FakeApiService()
        return retrofit.create(ApiService::class.java)
    }
}