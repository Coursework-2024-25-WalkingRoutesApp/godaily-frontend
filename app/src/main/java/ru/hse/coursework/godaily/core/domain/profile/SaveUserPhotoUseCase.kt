package ru.hse.coursework.godaily.core.domain.profile

import android.net.Uri
import retrofit2.Response
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.service.PhotoConverterService
import javax.inject.Inject

class SaveUserPhotoUseCase @Inject constructor(
    private val api: ApiService,
    private val photoConverterService: PhotoConverterService,
) {
    suspend fun execute(
        imageUri: Uri?
    ): Response<String> {
        val photo = imageUri?.let { photoConverterService.uriToByteArray(it) }
        return api.saveUserPhoto(
            "",
            photo
        )
    }
}