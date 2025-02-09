package ru.hse.coursework.godaily.core.domain.service

import android.net.Uri
import com.yalantis.ucrop.UCrop
import java.io.File

class CropRoutePreviewService : CropService {
    override fun startCrop(
        uri: Uri,
        context: android.content.Context,
        cropLauncher: androidx.activity.result.ActivityResultLauncher<android.content.Intent>
    ) {
        val destinationUri = Uri.fromFile(File(context.cacheDir, "cropped_route_image.jpg"))

        val uCrop = UCrop.of(uri, destinationUri)
            .withAspectRatio(3f, 4f)
            .withMaxResultSize(1080, 1080)
            .withOptions(getCropOptions())

        cropLauncher.launch(uCrop.getIntent(context))
    }

    override fun getCropOptions(): UCrop.Options {
        return UCrop.Options().apply {
            setCompressionQuality(100)
            setHideBottomControls(false)
            setFreeStyleCropEnabled(false)
            setShowCropGrid(true)
        }
    }
}