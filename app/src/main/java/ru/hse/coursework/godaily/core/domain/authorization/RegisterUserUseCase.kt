package ru.hse.coursework.godaily.core.domain.authorization

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(
        email: String,
        password: String,
        username: String
    ): ApiCallResult<String> {
        return safeApiCaller.safeApiCall { api.registerUser(email, password, username) }
    }
}