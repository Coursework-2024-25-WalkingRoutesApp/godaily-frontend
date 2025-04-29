package ru.hse.coursework.godaily.core.domain.profile

import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import javax.inject.Inject

class SaveUserEditedNameUseCase @Inject constructor(
    private val api: ApiService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(
        editedName: String,
    ): ApiCallResult<String> {
        return safeApiCaller.safeApiCall {
            api.saveUserEditedName(
                editedName
            )
        }
    }
}