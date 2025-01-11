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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.molecules.PublishButtonBig
import ru.hse.coursework.godaily.ui.theme.RobotoFontFamily
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun NewPublishNotification(
    onMyRoutesClick: () -> Unit,
    onHomeClick: () -> Unit
) {
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
                text = "Ура! Вы опубликовали\nновый маршрут!",
                fontFamily = RobotoFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = "Вы сможете просматривать созданные\nмаршруты в профиле",
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Light,
                color = greyDark,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(13.dp))

            PublishButtonBig(onClick = onMyRoutesClick, "К моим маршрутам")

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = "На главную",
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onHomeClick() }
            )
        }
    }
}

@Preview
@Composable
fun NewPublishNotificationPreview() {
    NewPublishNotification(
        onMyRoutesClick = {},
        onHomeClick = {}
    )
}
