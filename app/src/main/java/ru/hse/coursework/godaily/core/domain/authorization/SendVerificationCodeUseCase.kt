package ru.hse.coursework.godaily.core.domain.authorization

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller,
) {
    suspend fun execute(
        email: String
    ): ApiCallResult<Any> {
        val responseResult = safeApiCaller.safeApiCall { api.sendVerificationCode(email) }

        if (responseResult is ApiCallResult.Success) {
            return ApiCallResult.Success(true)
        }
        return responseResult
    }
}