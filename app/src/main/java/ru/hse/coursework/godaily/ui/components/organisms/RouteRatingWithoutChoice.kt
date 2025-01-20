package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.theme.greyLight

//TODO: оценки склонение
@Composable
fun RouteRatingWithoutChoice(
    rating: Double,
    reviewsCount: Int,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .border(width = 3.dp, color = greyLight, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .size(375.dp, 55.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VariableMedium(text = String.format("%.1f", rating), fontSize = 30.sp)

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        repeat(5) { index ->
                            Icon(
                                painter = painterResource(
                                    id = if (index < rating.toInt()) {
                                        R.drawable.star_enabled
                                    } else {
                                        R.drawable.star_disabled
                                    }
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(13.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }

                    VariableLight(
                        text = "$reviewsCount оценок",
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RouteRatingWithoutChoicePreview() {
    RouteRatingWithoutChoice(
        rating = 3.5,
        reviewsCount = 6,
    )
}

