package ru.hse.coursework.godaily.screen.map

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.yalantis.ucrop.UCrop
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.HeaderWithBackground
import ru.hse.coursework.godaily.ui.components.molecules.PublishButton
import ru.hse.coursework.godaily.ui.components.molecules.ToDraftsButton
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteInfoFields
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark
import java.io.File

@Composable
fun CreateRouteInfoScreen(
    navController: NavController,
    viewModel: CreateRouteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val croppedUri = UCrop.getOutput(result.data!!)
                selectedImageUri.value = croppedUri
            }
        }
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { startCrop(it, context, cropLauncher) }
        }
    )


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 65.dp)
        ) {
            HeaderWithBackground(header = "Создай свой маршрут")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                RouteInfoFields(
                    title = viewModel.routeTitle,
                    description = viewModel.routeDescription,
                    startPoint = viewModel.startPoint,
                    endPoint = viewModel.endPoint,
                    chosenCategories = viewModel.chosenCategories
                )

                Row(
                    modifier = Modifier.padding(top = 50.dp, bottom = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VariableMedium(
                        text = "Прикрепите фото маршрута",
                        fontSize = 20.sp,
                        fontColor = greyDark
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.clip),
                        contentDescription = null,
                        tint = black,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { imagePickerLauncher.launch("image/*") }
                    )
                }

                selectedImageUri.value?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "Выбранное изображение",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomCenter),
        ) {
            ToDraftsButton(onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.weight(1f))
            PublishButton(onClick = { /*TODO*/ })
        }
    }
}

private fun startCrop(
    uri: Uri,
    context: android.content.Context,
    cropLauncher: androidx.activity.result.ActivityResultLauncher<android.content.Intent>
) {
    val destinationUri = Uri.fromFile(File(context.cacheDir, "cropped_image.jpg"))

    val uCrop = UCrop.of(uri, destinationUri)
        .withAspectRatio(3f, 4f)
        .withMaxResultSize(1080, 1080)
        .withOptions(getCropOptions())

    cropLauncher.launch(uCrop.getIntent(context))
}

private fun getCropOptions(): UCrop.Options {
    return UCrop.Options().apply {
        setCompressionQuality(100)
        setHideBottomControls(false)
        setFreeStyleCropEnabled(false)
        setShowCropGrid(true)
        //setCircleDimmedLayer(true)
    }
}
