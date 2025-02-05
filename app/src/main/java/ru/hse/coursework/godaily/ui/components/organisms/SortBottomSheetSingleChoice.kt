package ru.hse.coursework.godaily.ui.components.organisms

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.ApplyButton
import ru.hse.coursework.godaily.ui.components.molecules.Quit
import ru.hse.coursework.godaily.ui.components.quarks.RadioButtonToggle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheetSingleChoice(
    selectedOption: MutableState<Int>,
    onApply: (Int) -> Unit,
    onReset: () -> Unit,
    showSortSheet: MutableState<Boolean>,
    options: List<String> = listOf(
        "Длинные",
        "Короткие",
        "Ближе ко мне",
        "С высоким рейтингом"
    )
) {
    ModalBottomSheet(
        onDismissRequest = { showSortSheet.value = false },
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    VariableMedium(
                        text = "Сначала показывать",
                        fontSize = 20.sp,
                    )

                    Quit(onClick = onReset)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 Выбор опций
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    options.forEachIndexed { index, option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOption.value = index }
                        ) {
                            RadioButtonToggle(
                                isChosen = selectedOption.value == index,
                                onToggle = { isChosen ->
                                    if (isChosen) selectedOption.value = index
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

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 Кнопка "Применить"
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    ApplyButton(onClick = { onApply(selectedOption.value) })
                }
            }
        }
    }
}

@Preview
@Composable
fun SortBottomSheetSingleChoicePreview() {
    SortBottomSheetSingleChoice(
        selectedOption = mutableStateOf(1),
        onApply = { selected -> println("Выбрана опция: $selected") },
        onReset = {},
        showSortSheet = mutableStateOf(true)
    )
}
