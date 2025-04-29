package ru.hse.coursework.godaily.core.di


import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

import ru.hse.coursework.godaily.core.security.VerificationManager

class JWTInterceptor(
    private val verificationManager: VerificationManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val jwt = runBlocking { verificationManager.jwtFlow.value }

        val modifiedRequest = if (jwt != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $jwt")
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(modifiedRequest)

        return response
    }
}
