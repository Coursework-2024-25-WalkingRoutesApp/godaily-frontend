package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    selectedItems: MutableState<Set<Int>>,
    onApply: (Set<Int>) -> Unit,
    onReset: () -> Unit,
    showFilterSheet: MutableState<Boolean>
) {
    val localSelectedItems: MutableState<Set<Int>> = mutableStateOf(setOf())
    localSelectedItems.value = selectedItems.value

    ModalBottomSheet(
        onDismissRequest = { showFilterSheet.value = false },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    VariableMedium(
                        text = "Выберите фильтры",
                        fontSize = 20.sp,
                    )

                    Quit(onClick = {
                        onReset()
                        showFilterSheet.value = false
                    })
                }

                Spacer(modifier = Modifier.height(16.dp))

                SelectableList(selectedItems = localSelectedItems)

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    ApplyButton(onClick = {
                        onApply(selectedItems.value)
                        selectedItems.value = localSelectedItems.value
                        showFilterSheet.value = false
                    })
                }
            }
        }
    }
}

@Preview
@Composable
fun SortBottomSheetPreview() {
    val selectedItems = mutableStateOf(setOf<Int>())
    val showFilterSheet = mutableStateOf(true)

    SortBottomSheet(
        selectedItems = selectedItems,
        onApply = { selected -> println("Выбраны фильтры: $selected") },
        onReset = { selectedItems.value = emptySet() },
        showFilterSheet = showFilterSheet
    )
}
