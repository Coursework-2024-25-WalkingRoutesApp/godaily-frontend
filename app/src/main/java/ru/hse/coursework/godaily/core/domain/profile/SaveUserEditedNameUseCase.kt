package ru.hse.coursework.godaily.core.domain.profile

import retrofit2.Response
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class SaveUserEditedNameUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(
        editedName: String,
    ): Response<String> {
        return api.saveUserEditedName(
            "",
            editedName
        )
    }
}