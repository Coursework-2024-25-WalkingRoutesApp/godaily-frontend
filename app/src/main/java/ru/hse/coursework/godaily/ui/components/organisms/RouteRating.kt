package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.greyLight

@Composable
fun RouteRating(
    rating: Double,
    reviewsCount: Int,
    onRatingSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedStars = remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .border(width = 3.dp, color = greyLight, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .size(338.dp, 139.dp)
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 7.dp),
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
                                modifier = Modifier.size(11.dp),
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

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                thickness = 1.dp,
                color = greyDark
            )

            VariableMedium(
                text = "Оцените маршрут",
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index < selectedStars.value) {
                                R.drawable.star_enabled
                            } else {
                                R.drawable.star_disabled
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                selectedStars.value = index + 1
                                onRatingSelected(selectedStars.value)
                            },
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RouteRatingPreview() {
    RouteRating(
        rating = 3.5,
        reviewsCount = 6,
        onRatingSelected = { selectedRating ->
            println("Selected rating: $selectedRating")
        }
    )
}

