package ru.hse.coursework.godaily.ui.errorsprocessing

import android.content.Context
import android.util.Log
import io.appmetrica.analytics.AppMetrica
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.security.VerificationManager
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val context: Context,
    private val verificationManager: VerificationManager
) {
    private companion object {
        const val TAG = "ErrorHandler"
    }

    fun handleError(error: ApiCallResult.Error) {
        val errorMessage = when {
            !error.message.isNullOrBlank() -> error.message
            error.throwable?.message?.isNotBlank() == true -> error.throwable.message
            else -> "Unknown error occurred"
        }

        val fullMessage = buildString {
            append("Error: ")
            append(errorMessage)
            error.throwable?.let { append(" | Exception: ${it.javaClass.simpleName}") }
        }

        AppMetrica.reportError(fullMessage, error.throwable ?: Exception(fullMessage))

        logError(error)

        showErrorMessage(error.copy(message = errorMessage))
    }

    private fun logError(error: ApiCallResult.Error) {
        Log.e(TAG, buildLogMessage(error), error.throwable ?: Exception(error.message))
    }

    private fun showErrorMessage(error: ApiCallResult.Error) {
        val toastManager = ToastManager(context)

        when (error.code) {
            500 -> {
                Log.w(TAG, "Server error occurred", error.throwable)
                toastManager.showToast("Ошибка при обращении к серверу. Проверьте подключение к интернету и повторите попытку")
            }

            401 -> {
                Log.w(TAG, "Unauthorized access attempt")
                toastManager.showToast("Ошибка доступа: ${error.message}")
                verificationManager.clearJwt()
                verificationManager.clearVerificationStatus()
            }

            498 -> {
                Log.i(TAG, "Token expired, clearing JWT")
                toastManager.showToast("Ваша сессия истекла. Зайдите в приложение заново")
                verificationManager.clearJwt()
                verificationManager.clearVerificationStatus()
            }

            //TODO убрать
            else -> {
                Log.w(TAG, "Unexpected error occurred")
                toastManager.showToast("Возникла ошибка: ${error.message}")
            }
        }
    }

    private fun buildLogMessage(error: ApiCallResult.Error): String {
        return StringBuilder().apply {
            append("Error code: ${error.code}")
            append(", Message: ${error.message}")
            error.throwable?.let {
                append(", Exception: ${it.javaClass.simpleName}")
            }
        }.toString()
    }
}