package ru.hse.coursework.godaily.core.domain.profile

import android.net.Uri
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.service.PhotoConverterService
import javax.inject.Inject

class SaveUserPhotoUseCase @Inject constructor(
    private val api: ApiService,
    private val photoConverterService: PhotoConverterService,
    private val safeApiCaller: SafeApiCaller
) {
    suspend fun execute(
        imageUri: Uri?
    ): ApiCallResult<String> {
        val photo = imageUri?.let { photoConverterService.uriToMultipart(it) }
        return safeApiCaller.safeApiCall {
            api.saveUserPhoto(
                photo
            )
        }
    }
}