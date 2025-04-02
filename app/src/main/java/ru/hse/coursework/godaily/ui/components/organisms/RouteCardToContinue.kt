import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import ru.hse.coursework.godaily.ui.theme.purpleDark

@Composable
fun RouteCardToContinue(
    distance: String,
    title: String,
    imageResUrl: String,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .size(width = 194.dp, height = 120.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onCardClick
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageResUrl,
                    error = painterResource(R.drawable.sample_route_image)
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                purpleDark,
                                purpleDark
                            )
                        )
                    )
                    .height(80.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            ) {
                RouteLengthSmall(distance = distance)
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 0.dp, bottom = 10.dp, end = 10.dp)
            ) {
                RouteNameSmall(
                    text = title,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RouteCardToContinuePreview() {
    RouteCardToContinue(
        distance = "4.6 км",
        title = "Историческое Измайлово",
        imageResUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlKC_brTH0YllYbrNf3gtJ6dSyGoDhI4eD-g&s",
        modifier = Modifier.padding(8.dp),
        onCardClick = {}
    )
}
