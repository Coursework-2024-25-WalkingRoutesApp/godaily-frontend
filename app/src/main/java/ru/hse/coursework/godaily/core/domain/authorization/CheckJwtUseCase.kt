package ru.hse.coursework.godaily.core.domain.authorization

import com.fasterxml.jackson.databind.ObjectMapper
import ru.hse.coursework.godaily.core.data.model.UserDto
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import javax.inject.Inject

class CheckJwtUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller,
    private val objectMapper: ObjectMapper
) {
    suspend fun execute(): ApiCallResult<Any> {
        val getInfoResponse = safeApiCaller.safeApiCall { api.getUserInfo() }

        if (getInfoResponse is ApiCallResult.Error) {
            return getInfoResponse
        } else if (getInfoResponse is ApiCallResult.Success) {
            return try {
                ApiCallResult.Success(
                    objectMapper.convertValue(
                        getInfoResponse.data,
                        UserDto::class.java
                    )
                )
            } catch (e: Exception) {
                ApiCallResult.Error(null, "Не удалось получить информацию профиля")
            }
        }
        return ApiCallResult.Error(null, "Не удалось получить информацию профиля")
    }
}