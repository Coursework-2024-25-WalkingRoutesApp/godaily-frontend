package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.purpleLight

@Composable
fun ProfileLittleCard(
    type: String = "Черновики",
    cardColor: Color = purpleLight,
    routeCount: Int,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val secondaryTextColor = greyDark
    val routeLabel = when {
        routeCount % 10 == 1 && routeCount % 100 != 11 -> "маршрут"
        routeCount % 10 in 2..4 && (routeCount % 100 < 10 || routeCount % 100 >= 20) -> "маршрута"
        else -> "маршрутов"
    }

    Card(
        modifier = modifier
            .size(width = 167.dp, height = 97.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, bottom = 15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            VariableLight(
                text = type,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            VariableLight(
                text = "$routeCount $routeLabel",
                fontSize = 14.sp,
                fontColor = secondaryTextColor
            )
        }
    }
}

@Preview
@Composable
fun ProfileLittleCardPreview() {
    ProfileLittleCard(routeCount = 21)
}
