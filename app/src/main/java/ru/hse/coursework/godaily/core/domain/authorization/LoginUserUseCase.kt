package ru.hse.coursework.godaily.core.domain.authorization

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val api: ApiService,
) {
    suspend fun execute(
        email: String,
        password: String
    ): Response<String> {
        return api.loginUser(email, password)
    }
}