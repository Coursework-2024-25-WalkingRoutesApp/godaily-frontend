package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.PublishButton
import ru.hse.coursework.godaily.ui.components.molecules.ToDraftsButton
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun RouteFinishDialog(
    showDialog: MutableState<Boolean>,
    mark: MutableState<Int>,
    feedbackText: MutableState<String>,
    onSaveClick: () -> Unit,
    onNotSaveClick: () -> Unit
) {
    val selectedStars = remember { mutableStateOf(0) }

    if (showDialog.value) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .size(width = 343.dp, height = 550.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(top = 25.dp, bottom = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Поздравляем!",
                        fontFamily = RobotoFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(13.dp))

                    Text(
                        text = "+1 пройденный маршрут",
                        fontSize = 16.sp,
                        fontFamily = RobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        color = greyDark,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    VariableMedium(
                        text = "Оцените маршрут",
                        fontSize = 15.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(5) { index ->
                            Icon(
                                painter = painterResource(
                                    id = if (index < mark.value) {
                                        R.drawable.star_enabled
                                    } else {
                                        R.drawable.star_disabled
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        mark.value = index + 1
                                    },
                                tint = Color.Unspecified
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    ReviewBox(feedbackText = feedbackText)
                }
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                ) {
                    ToDraftsButton(
                        onClick = onNotSaveClick,
                        text = "Не сохранять",
                        modifier = Modifier.width(140.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    PublishButton(
                        onClick = onSaveClick,
                        text = "Сохранить",
                        modifier = Modifier.width(140.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RouteFinishDialogPreview() {
    RouteFinishDialog(
        showDialog = mutableStateOf(true),
        mark = mutableStateOf(5),
        feedbackText = mutableStateOf(""),
        onSaveClick = {},
        onNotSaveClick = {}
    )
}
