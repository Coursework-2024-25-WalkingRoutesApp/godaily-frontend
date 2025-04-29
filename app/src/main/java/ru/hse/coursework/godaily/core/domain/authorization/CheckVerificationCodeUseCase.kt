package ru.hse.coursework.godaily.core.domain.authorization

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import javax.inject.Inject

class CheckVerificationCodeUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller,
) {
    suspend fun execute(
        code: String
    ): ApiCallResult<Any> {
        val responseResult = safeApiCaller.safeApiCall { api.checkVerificationCode(code) }

        if (responseResult is ApiCallResult.Success) {
            return ApiCallResult.Success(true)
        }
        return responseResult
    }
}