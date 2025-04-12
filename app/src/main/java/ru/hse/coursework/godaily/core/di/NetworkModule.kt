package ru.hse.coursework.godaily.core.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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
import retrofit2.converter.scalars.ScalarsConverterFactory
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
        return ObjectMapper().apply {
            registerModule(JavaTimeModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
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
    fun provideRetrofit(json: Json, okHttp: Lazy<Call.Factory>, mapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("http://10.51.171.246:8080/") //LTE security
            .baseUrl("http://192.168.0.65:8080/") //домашний security
            //.baseUrl("http://10.51.171.246:8081/") //LTE routes
            //.baseUrl("http://192.168.0.65:8081/") //домашний routes
            .callFactory { okHttp.get().newCall(it) }
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                JacksonConverterFactory.create(mapper)
                //TODO удалить сеттингс с хардкодом пути
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