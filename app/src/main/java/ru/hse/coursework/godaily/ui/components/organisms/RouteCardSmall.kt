import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.RouteNameSmall
import ru.hse.coursework.godaily.ui.components.molecules.RouteLengthSmall
import ru.hse.coursework.godaily.ui.components.molecules.RouteTimeSmall
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun RouteCardSmall(
    distance: String,
    time: String,
    title: String,
    imageResUrl: String,
    categories: List<Int>,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .size(width = 165.dp, height = 220.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onCardClick
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageResUrl,
                    error = painterResource(R.drawable.route_default)
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                lime,
                                lime
                            )
                        )
                    )
                    .padding(bottom = 10.dp, end = 10.dp, start = 0.dp)
                    .wrapContentHeight()
            ) {
                RouteTimeSmall(routeTime = time)
                Spacer(modifier = Modifier.height(4.dp))
                RouteNameSmall(text = title, modifier = Modifier.padding(start = 10.dp))
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            ) {
                RouteLengthSmall(distance = distance)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
            ) {
                CategoryIconsGrid(categories = categories)
            }
        }
    }
}


@Composable
fun CategoryIconsGrid(
    categories: List<Int>,
    modifier: Modifier = Modifier
) {
    val icons = categories.take(4)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,

        ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            icons.take(2).forEach { iconRes ->
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            icons.drop(2).forEach { iconRes ->
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RouteCardSmallPreview() {
    RouteCardSmall(
        distance = "4.6 км",
        time = "35-50 минут",
        title = "Историческое Измайлово",
        imageResUrl = "https://i.postimg.cc/nhTR2SBF/history.jpg",
        modifier = Modifier.padding(8.dp),
        categories = listOf(
            R.drawable.culture,
            R.drawable.coffee,
            R.drawable.metro,
            R.drawable.nature
        ),
        onCardClick = {}
    )
}
