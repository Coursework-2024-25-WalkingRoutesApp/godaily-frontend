package ru.hse.coursework.godaily.ui.components.quarks

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun FavouriteToggle(
    isFavorite: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onToggle(!isFavorite) },
        modifier = modifier.size(40.dp, 35.dp)
    ) {
        Icon(
            painter = if (isFavorite) painterResource(id = R.drawable.heart_enabled) else painterResource(
                id = R.drawable.heart_disabled
            ),
            contentDescription = null,
            modifier = Modifier.size(35.dp, 32.dp),
            tint = greyDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavouriteTogglePreview() {
    val isFavorite = remember { mutableStateOf(false) }
    FavouriteToggle(
        isFavorite = isFavorite.value,
        onToggle = { isFavorite.value = it }
    )
}
