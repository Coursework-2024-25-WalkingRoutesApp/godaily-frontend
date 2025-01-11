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
import ru.hse.coursework.godaily.ui.components.quarks.RadioButtonToggle
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun SortBottomSheetSingleChoice(
    selectedOption: Int,
    onClose: () -> Unit,
    onApply: (Int) -> Unit,
    onReset: () -> Unit,
    options: List<String> = listOf(
        "Длинные",
        "Короткие",
        "Ближе ко мне",
        "С высоким рейтингом"
    )
) {
    val selectedItem = remember { mutableStateOf(selectedOption) }

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
                        .size(16.dp)
                        .clickable { onClose() }
                )

                Spacer(modifier = Modifier.weight(3.5f))

                Box {
                    VariableMedium(
                        text = "Сначала показывать",
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.weight(0.6f))
                Quit(onClick = onReset)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEachIndexed { index, option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedItem.value = index
                            }
                    ) {
                        RadioButtonToggle(
                            isChosen = selectedItem.value == index,
                            onToggle = { isChosen ->
                                if (isChosen) selectedItem.value = index
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        VariableMedium(
                            text = option,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ApplyButton(onClick = { onApply(selectedItem.value) })
        }
    }
}


@Preview
@Composable
fun SortBottomSheetSingleChoicePreview() {
    SortBottomSheetSingleChoice(
        selectedOption = 1,
        onClose = {},
        onApply = { selected -> println("Выбрана опция: $selected") },
        onReset = { println("Сброс фильтров") },
    )
}
