package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.hse.coursework.godaily.ui.components.molecules.CustomTextField
import ru.hse.coursework.godaily.ui.components.molecules.CustomTextWindow
import ru.hse.coursework.godaily.ui.components.molecules.PublishButtonBig
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun AddPointTitleDialog(
    showDialog: MutableState<Boolean>,
    title: MutableState<String>,
    description: MutableState<String>,
    onSaveClick: (String, String) -> Unit,
    onKeepWithNoTitleClick: () -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Box(
                modifier = Modifier
                    .size(width = 338.dp, height = 450.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(top = 25.dp, bottom = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = "Добавьте \nинформацию о точке",
                            fontFamily = RobotoFontFamily,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            textAlign = TextAlign.Start
                        )

                        CustomTextField(
                            text = title,
                            onValueChange = { title.value = it },
                            placeholder = "Название",
                            isRequired = true,
                        )
                        Spacer(modifier = Modifier.height(13.dp))

                        CustomTextWindow(
                            text = description,
                            placeholder = "Описание точки"
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    PublishButtonBig(onClick = {
                        showDialog.value = false
                        onSaveClick(title.value, description.value)
                    }, text = "Сохранить")

                    Spacer(modifier = Modifier.height(13.dp))

                    Text(
                        text = "Оставить точку без названия",
                        fontSize = 16.sp,
                        fontFamily = RobotoFontFamily,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            showDialog.value = false
                            onKeepWithNoTitleClick()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddPointTitleDialogPreview() {
    AddPointTitleDialog(
        showDialog = mutableStateOf(true),
        title = mutableStateOf(""),
        description = mutableStateOf(""),
        onSaveClick = { _, _ -> },
        onKeepWithNoTitleClick = {}
    )
}
