package ru.hse.coursework.godaily.core.domain.profile

import android.net.Uri
import retrofit2.Response
import ru.hse.coursework.godaily.core.data.network.ApiService
import javax.inject.Inject

class SaveUserProfileInfoUseCase @Inject constructor(
    private val api: ApiService
) {
    suspend fun execute(
        editedName: String,
        imageUri: Uri?
    ): Response<String> {
        val photo = convertUriToSomething(imageUri)
        return api.saveUserInfo(
            "",
            editedName,
            photo
        )
    }

    //TODO
    private fun convertUriToSomething(uri: Uri?): String {
        return ""
    }
}