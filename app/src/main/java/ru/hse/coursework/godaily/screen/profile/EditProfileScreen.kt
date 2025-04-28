package ru.hse.coursework.godaily.screen.profile

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.yalantis.ucrop.UCrop
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.service.CropProfilePhotoService
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.CustomTextField
import ru.hse.coursework.godaily.ui.components.molecules.HeaderWithBackground
import ru.hse.coursework.godaily.ui.components.molecules.StartButton
import ru.hse.coursework.godaily.ui.components.superorganisms.LoadingScreenWrapper
import ru.hse.coursework.godaily.ui.notification.ToastManager
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark


@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
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
//        val imagePickerLauncher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.GetContent(),
//            onResult = { uri: Uri? ->
//                uri?.let { CropProfilePhotoService().startCrop(it, context, cropLauncher) }
//            }
//        )

        LaunchedEffect(Unit) {
            viewModel.loadUserData()
        }


        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 65.dp)
            ) {
                Box {
                    HeaderWithBackground(header = "Редактируй свой профиль")
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Back(
                            onClick = { navController.popBackStack() }
                        )
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    CustomTextField(
                        text = viewModel.editedUserName,
                        onValueChange = {},
                        placeholder = "Имя пользователя",
                        isRequired = true,
                        maxLength = 30
                    )

                    Row(
                        modifier = Modifier.padding(top = 50.dp, bottom = 30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VariableMedium(
                            text = "Прикрепите фото для профиля",
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
                                .size(200.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(CircleShape)
                                .background(Color.LightGray, shape = CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .align(Alignment.BottomCenter),
            ) {
                StartButton(
                    onClick = {
                        viewModel.saveNewUserData(context) {
                            navController.popBackStack()
                        }
                    },
                    text = "Сохранить"
                )
            }
        }
    }
}