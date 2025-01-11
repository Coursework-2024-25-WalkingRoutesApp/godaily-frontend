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
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.RouteNameSmall
import ru.hse.coursework.godaily.ui.components.molecules.RouteLengthSmall
import ru.hse.coursework.godaily.ui.theme.purpleDark

@Composable
fun RouteCardToContinue(
    distance: String,
    title: String,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    // TODO: сделать градиент динамически подстраиваемым под текст, ограничить длину текста на поля в логике
    Card(
        modifier = modifier
            .size(width = 194.dp, height = 120.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
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
        imageRes = R.drawable.sample_route_image,
        modifier = Modifier.padding(8.dp),
    )
}
