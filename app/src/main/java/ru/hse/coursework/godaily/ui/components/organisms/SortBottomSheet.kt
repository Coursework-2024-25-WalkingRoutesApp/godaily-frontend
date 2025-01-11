package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.ApplyButton
import ru.hse.coursework.godaily.ui.components.molecules.Quit
import ru.hse.coursework.godaily.ui.components.quarks.SelectableList
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun SortBottomSheet(
    selectedItems: MutableState<Set<Int>>,
    onClose: () -> Unit,
    onApply: (Set<Int>) -> Unit,
    onReset: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(width = 390.dp, height = 260.dp)
            .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = "Close",
                    tint = greyDark,
                    modifier = Modifier
                        .size(13.dp)
                        .clickable { onClose() }
                )

                Spacer(modifier = Modifier.weight(1.6f))

                Box {
                    VariableMedium(
                        text = "Показать",
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Quit(onClick = onReset)

            }

            Spacer(modifier = Modifier.height(8.dp))

            SelectableList(
                selectedItems = selectedItems,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ApplyButton(onClick = { onApply(selectedItems.value) })
        }
    }
}

@Preview
@Composable
fun SortBottomSheetPreview() {
    val selectedItems = remember { mutableStateOf(setOf<Int>()) }
    SortBottomSheet(
        selectedItems = selectedItems,
        onClose = {},
        onApply = { selected -> println("Выбраны элементы: $selected") },
        onReset = {}
    )
}
