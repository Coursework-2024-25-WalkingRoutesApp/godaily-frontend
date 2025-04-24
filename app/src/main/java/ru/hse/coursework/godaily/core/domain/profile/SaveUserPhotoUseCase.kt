package ru.hse.coursework.godaily.core.domain.profile

import android.net.Uri
import coil3.ImageLoader
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.hse.coursework.godaily.core.data.network.ApiService
import ru.hse.coursework.godaily.core.domain.apiprocessing.ApiCallResult
import ru.hse.coursework.godaily.core.domain.apiprocessing.SafeApiCaller
import ru.hse.coursework.godaily.core.domain.service.PhotoConverterService
import javax.inject.Inject

class SaveUserPhotoUseCase @Inject constructor(
    private val api: ApiService,
    private val photoConverterService: PhotoConverterService,
    private val safeApiCaller: SafeApiCaller,
    private val imageLoader: ImageLoader
) {
    suspend fun execute(
        imageUri: Uri?,
        photoUrl: String?
    ): ApiCallResult<String> {

        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()

        val userPhotoMultipart = imageUri?.let { photoConverterService.uriToMultipart(it) }

        userPhotoMultipart?.let { multipart ->
            val typePart = "user".toRequestBody("text/plain".toMediaType())
            val photoUrlPart = photoUrl
                ?.takeIf { it.isNotBlank() }
                ?.toRequestBody("text/plain".toMediaType())

            val savePhotoResult = safeApiCaller.safeApiCall {
                api.uploadPhoto(multipart, typePart, photoUrlPart)
            }

            if (savePhotoResult is ApiCallResult.Success) {
                val userPreview = savePhotoResult.data
                return safeApiCaller.safeApiCall {
                    api.saveUserPhoto(userPreview)
                }
            }
        }
        return ApiCallResult.Error(null, "Не удалось сохранить фотографию")
    }
}