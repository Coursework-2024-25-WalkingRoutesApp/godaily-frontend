package ru.hse.coursework.godaily.core.domain.apiprocessing

sealed class ApiCallResult<out T> {
    data class Success<T>(val data: T) : ApiCallResult<T>()
    data class Error(
        val code: Int?,
        val message: String?,
        val throwable: Throwable? = null
    ) : ApiCallResult<Nothing>()
}
