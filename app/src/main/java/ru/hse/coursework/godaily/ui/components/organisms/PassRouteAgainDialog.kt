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
import ru.hse.coursework.godaily.ui.components.molecules.PublishButtonBig
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily

@Composable
fun PassRouteAgainDialog(
    showDialog: MutableState<Boolean>,
    passAgain: () -> Unit,
    onHomeClick: () -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .size(width = 334.dp, height = 243.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(top = 25.dp, bottom = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Вы уже проходили этот маршрут",
                        fontFamily = RobotoFontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(13.dp))

                    Spacer(modifier = Modifier.height(40.dp))

                    PublishButtonBig(onClick = {
                        showDialog.value = false
                        passAgain()
                    }, text = "Пройти заново")

                    Spacer(modifier = Modifier.height(13.dp))

                    Text(
                        text = "На главную",
                        fontSize = 16.sp,
                        fontFamily = RobotoFontFamily,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            showDialog.value = false
                            onHomeClick()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PassRouteAgainDialogPreview() {
    PassRouteAgainDialog(
        showDialog = mutableStateOf(true),
        passAgain = {},
        onHomeClick = {}
    )
}
