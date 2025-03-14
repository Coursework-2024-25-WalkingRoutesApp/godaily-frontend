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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.domain.service.CropRoutePreviewService
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.HeaderWithBackground
import ru.hse.coursework.godaily.ui.components.molecules.PublishButton
import ru.hse.coursework.godaily.ui.components.molecules.Question
import ru.hse.coursework.godaily.ui.components.molecules.ToDraftsButton
import ru.hse.coursework.godaily.ui.components.organisms.NewPublishDialog
import ru.hse.coursework.godaily.ui.components.organisms.PublishWarningDialog
import ru.hse.coursework.godaily.ui.components.organisms.UnsuccessfulPublishDialog
import ru.hse.coursework.godaily.ui.components.superorganisms.RouteInfoFields
import ru.hse.coursework.godaily.ui.navigation.BottomNavigationItem
import ru.hse.coursework.godaily.ui.navigation.NavigationItem
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun CreateRouteInfoScreen(
    navController: NavController,
    bottomNavController: NavHostController,
    viewModel: CreateRouteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val selectedImageUri = viewModel.selectedImageUri

    val coroutineScope = rememberCoroutineScope()

    val showPublishWarningDialog = viewModel.showPublishWarningDialog
    val showNewPublishDialog = viewModel.showNewPublishDialog
    val showUnsuccessfulPublishDialog = viewModel.showUnsuccessfulPublishDialog

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
            uri?.let { CropRoutePreviewService().startCrop(it, context, cropLauncher) }
        }
    )


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 65.dp)
        ) {
            Box {
                HeaderWithBackground(header = "Создай свой маршрут")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Back(
                        onClick = { navController.popBackStack() }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Question(onClick = {/*TODO*/ })

                }
            }


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
            ToDraftsButton(onClick = {
                coroutineScope.launch {
                    viewModel.saveRouteToDrafts(context)
                    bottomNavController.navigate(BottomNavigationItem.Routes.route) {
                        popUpTo(bottomNavController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            })
            Spacer(modifier = Modifier.weight(1f))
            PublishButton(onClick = {
                showPublishWarningDialog.value = true
            })
        }
    }

    if (showPublishWarningDialog.value) {
        PublishWarningDialog(
            showDialog = showPublishWarningDialog,
            onPublishClick = {
                coroutineScope.launch {
                    val isSuccess = viewModel.publishRoute(context)
                    if (isSuccess) {
                        showNewPublishDialog.value = true
                    } else {
                        showUnsuccessfulPublishDialog.value = true
                    }
                }
            }
        )
    }

    if (showUnsuccessfulPublishDialog.value) {
        // TODO протестировать
        UnsuccessfulPublishDialog(
            showDialog = showUnsuccessfulPublishDialog,
            tryAgain = {
                coroutineScope.launch {
                    val isSuccess = viewModel.publishRoute(context)
                    if (isSuccess) {
                        showNewPublishDialog.value = true
                    } else {
                        showUnsuccessfulPublishDialog.value = true
                    }
                }
            },
            onHomeClick = {
                bottomNavController.navigate(BottomNavigationItem.Home.route) {
                    popUpTo(bottomNavController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        )
    }

    if (showNewPublishDialog.value) {
        NewPublishDialog(
            showDialog = showNewPublishDialog,
            onMyRoutesClick = {
                navController.navigate(NavigationItem.RoutesMain.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }

            },
            onHomeClick = {
                //TODO: пофиксить, чтобы нигде, где не надо, нельзя перейти назад с помощью свайпа
                bottomNavController.navigate(BottomNavigationItem.Home.route) {
                    popUpTo(bottomNavController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

