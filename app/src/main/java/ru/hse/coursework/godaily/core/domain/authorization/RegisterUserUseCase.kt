package ru.hse.coursework.godaily.core.domain.authorization

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val api: ApiService,
) {
    suspend fun execute(
        email: String,
        password: String,
        username: String
    ): Response<String> {
        return api.registerUser(email, password, username)
    }
}