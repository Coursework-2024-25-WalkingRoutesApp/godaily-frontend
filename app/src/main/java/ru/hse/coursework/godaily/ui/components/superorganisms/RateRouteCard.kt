package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.RouteDestinations
import ru.hse.coursework.godaily.ui.components.atoms.RouteNameBig
import ru.hse.coursework.godaily.ui.components.organisms.ReviewBox


@Composable
fun RateRouteCard(
    imageLoader: ImageLoader,
    title: String,
    startPoint: String,
    endPoint: String,
    imageUrl: String,
    mark: MutableState<Int>,
    reviewText: MutableState<String>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUrl,
                imageLoader = imageLoader,
                error = painterResource(R.drawable.route_to_continue_default)
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
        ) {
            RouteNameBig(text = title)

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RouteDestinations(start = startPoint, end = endPoint)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                        .size(40.dp)
                        .padding(horizontal = 4.dp)
                        .clickable {
                            mark.value = index + 1
                        },
                    tint = Color.Unspecified
                )
            }
        }

        ReviewBox(
            feedbackText = reviewText,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RateRouteCardPreview() {
    RateRouteCard(
        imageLoader = ImageLoader(LocalContext.current),
        title = "Измайловский Кремль",
        startPoint = "р-он. Измайлово",
        endPoint = "Измайловское шоссе, 73",
        imageUrl = "",
        mark = mutableStateOf(3),
        reviewText = mutableStateOf("")
    )
}

