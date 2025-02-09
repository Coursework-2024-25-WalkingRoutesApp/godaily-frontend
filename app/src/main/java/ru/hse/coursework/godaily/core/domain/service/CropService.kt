package ru.hse.coursework.godaily.core.domain.service

import android.net.Uri
import com.yalantis.ucrop.UCrop

interface CropService {
    fun startCrop(
        uri: Uri,
        context: android.content.Context,
        cropLauncher: androidx.activity.result.ActivityResultLauncher<android.content.Intent>
    )

    fun getCropOptions(): UCrop.Options
}