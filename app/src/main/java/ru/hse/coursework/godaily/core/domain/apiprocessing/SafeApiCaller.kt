package ru.hse.coursework.godaily.core.domain.apiprocessing

import retrofit2.Response

class SafeApiCaller {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiCallResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                ApiCallResult.Success(response.body()!!)
            } else {
                ApiCallResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            ApiCallResult.Error(null, e.message, e)
        }
    }

}