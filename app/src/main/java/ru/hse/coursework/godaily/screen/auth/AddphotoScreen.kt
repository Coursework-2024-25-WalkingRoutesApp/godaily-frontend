package ru.hse.coursework.godaily.screen.auth

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.yalantis.ucrop.UCrop
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.service.CropProfilePhotoService
import ru.hse.coursework.godaily.ui.components.atoms.VariableBold
import ru.hse.coursework.godaily.ui.components.molecules.StartButton
import ru.hse.coursework.godaily.ui.components.superorganisms.LoadingScreenWrapper
import ru.hse.coursework.godaily.ui.notification.ToastManager
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.greyLight

@Composable
fun AddPhotoScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val isLoading = viewModel.isLoading

    val context = LocalContext.current
    val selectedImageUri = viewModel.selectedImageUri

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val croppedUri = UCrop.getOutput(result.data!!)
                selectedImageUri.value = croppedUri
            }
        }
    )

//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent(),
//        onResult = { uri: Uri? ->
//            uri?.let { CropProfilePhotoService().startCrop(it, context, cropLauncher) }
//        }
//    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val fileSizeInBytes = CropProfilePhotoService().getImageSize(context, it)
                val maxSizeInBytes = 20 * 1024 * 1024

                if (fileSizeInBytes > maxSizeInBytes) {
                    ToastManager(context).showToast("Файл слишком большой. Максимальный размер 20 МБ.")
                } else {
                    CropProfilePhotoService().startCrop(it, context, cropLauncher)
                }
            }
        }
    )


    LoadingScreenWrapper(isLoading = isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_auth),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = 0.dp, y = (-60).dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                VariableBold(
                    text = "Добавьте фотографию профиля",
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(80.dp))

                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .border(2.dp, greyLight, CircleShape)
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri.value == null) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Добавить фото",
                            tint = Color.Gray,
                            modifier = Modifier.size(70.dp)
                        )
                    } else {
                        AsyncImage(
                            model = selectedImageUri.value,
                            contentDescription = "Выбранное изображение",
                            modifier = Modifier
                                .size(300.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
                StartButton(
                    text = "Продолжить",
                    onClick = {
                        viewModel.addProfilePhoto()
                        viewModel.saveVerificationStatusToStorage()
                    }
                )
                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Пропустить",
                    textDecoration = TextDecoration.Underline,
                    fontSize = 16.sp,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable {
                        viewModel.saveVerificationStatusToStorage()
                    }
                )
            }
        }
    }
}
