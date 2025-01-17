package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun ReviewCard(
    userName: String,
    photoUrl: String,
    date: String,
    mark: Int,
    reviewText: String
) {
    Card(
        colors = CardColors(
            containerColor = Color.Unspecified,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = photoUrl,
                        error = painterResource(R.drawable.default_profile_photo)
                    ),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.width(16.dp))
                VariableLight(text = userName, fontSize = 25.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index < mark) {
                                R.drawable.star_enabled
                            } else {
                                R.drawable.star_disabled
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                VariableLight(text = date, fontSize = 13.sp, fontColor = greyDark)
            }

            Spacer(modifier = Modifier.height(8.dp))

            VariableLight(
                text = reviewText,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewCardPreview() {
    ReviewCard(
        userName = "Игорь",
        photoUrl = "",
        date = "11 апреля",
        mark = 4,
        reviewText = "Отлично провел время, прогуливаясь по району Измайлово в Москве! Это был увлекательный опыт, наполненный историей, культурой и красотой природы. Особенно порадовало то, что прогулка не заняла много времени, что было очень удобно. Измайловский Кремль оказался настоящим историческим кладом, а усадьба Измайлово поразила своей архитектурой. Прогулка вдоль Серебряно-Виноградного пруда была умиротворяющей и позволила насладиться живописными видами. Рекомендую эту прогулку всем, кто хочет провести время с пользой и насладиться красотой Москвы!"
    )
}
