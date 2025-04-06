package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium

@Composable
fun UserProfile(
    userName: String,
    onEditProfileClick: () -> Unit,
    profilePictureUrl: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = profilePictureUrl,
                error = painterResource(R.drawable.avatar_default)
            ),
            contentDescription = "User Profile Picture",
            modifier = Modifier
                .size(159.dp)
                .clip(CircleShape)
                .background(Color.LightGray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .width(178.dp)
                .height(59.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VariableMedium(
                    text = userName,
                    fontSize = 29.sp,
                )

                Spacer(modifier = Modifier.height(4.dp))

                VariableLight(
                    text = "Редактировать профиль",
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onEditProfileClick() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    UserProfile(
        userName = "Иван",
        onEditProfileClick = {}
    )
}
