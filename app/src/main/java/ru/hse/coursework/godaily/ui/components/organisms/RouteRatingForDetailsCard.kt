package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.greyDark


@Composable
fun RouteRatingForDetailsCard(
    rating: Double,
    reviewsCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.star_enabled
                ),
                contentDescription = null,
                modifier = Modifier.size(35.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            VariableLight(text = String.format("%.1f", rating), fontSize = 30.sp)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            VariableLight(text = "$reviewsCount отзывов", fontSize = 15.sp, fontColor = greyDark)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.arrow_dropdown),
                contentDescription = "Arrow",
                modifier = Modifier
                    .size(13.dp)
                    .rotate(-90F),
                tint = greyDark
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RouteRatingForDetailsCardPreview() {
    RouteRatingForDetailsCard(
        rating = 4.75,
        reviewsCount = 79
    )
}

