package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.quarks.CheckboxToggle
import ru.hse.coursework.godaily.ui.theme.greyDark
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun SelectableList(
    selectedItems: MutableState<Set<Int>>,
    items: List<Pair<String, Int?>> = listOf(
        "Природный" to R.drawable.nature,
        "Культурно-исторический" to R.drawable.culture,
        "Кафе по пути" to R.drawable.coffee,
        "У метро" to R.drawable.metro
    )
) {
    Column(
        modifier = Modifier
            .width(331.dp)
    ) {
        items.forEachIndexed { index, item ->
            SelectableListItem(
                text = item.first,
                isChosen = selectedItems.value.contains(index),
                iconRes = item.second
            ) { isChosen ->
                selectedItems.value = if (isChosen) {
                    selectedItems.value + index
                } else {
                    selectedItems.value - index
                }
            }
        }
    }
}

@Composable
fun SelectableListItem(
    text: String,
    isChosen: Boolean,
    iconRes: Int? = null,
    onToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
    ) {
        CheckboxToggle(
            isChosen = isChosen,
            onToggle = onToggle
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.width(8.dp))

        iconRes?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = if (it != R.drawable.nature) greyDark else lime
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectableListPreview() {
    val selectedItems = remember { mutableStateOf(setOf<Int>()) }
    SelectableList(
        selectedItems = selectedItems
    )
}
