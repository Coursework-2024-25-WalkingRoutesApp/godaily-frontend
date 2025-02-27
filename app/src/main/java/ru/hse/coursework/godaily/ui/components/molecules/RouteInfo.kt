package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.theme.black
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun RouteInfo(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = lime,
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .size(51.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.route_info),
            contentDescription = null,
            tint = black,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview
@Composable
fun RouteInfoPreview() {
    RouteInfo(onClick = {})
}