package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.core.data.model.SortOption
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun SortDropdown(
    options: List<SortOption>,
    initialSelection: SortOption = SortOption.CLOSER_TO_ME,
    onOptionSelected: (SortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(initialSelection) }

    Box(
        modifier = modifier
            .width(129.dp)
            .height(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            VariableLight(
                text = when (selectedOption) {
                    //TODO: переделать
                    SortOption.CLOSER_TO_ME -> "ближе ко мне"
                    SortOption.HIGH_RATING -> "с высоким рейтингом"
                    SortOption.LONG -> "длинные"
                    SortOption.SHORT -> "короткие"
                    SortOption.UNDEFINED -> "неизвестный"
                },
                fontSize = 16.sp,
                fontColor = greyDark,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.arrow_dropdown),
                contentDescription = null,
                tint = greyDark,
                modifier = Modifier
                    .size(18.dp)
                    .padding(top = 3.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = option
                        expanded = false
                        onOptionSelected(option)
                    },
                    text = {
                        Text(
                            //todo: переделать
                            when (option) {
                                SortOption.CLOSER_TO_ME -> "ближе ко мне"
                                SortOption.HIGH_RATING -> "с высоким рейтингом"
                                SortOption.LONG -> "длинные"
                                SortOption.SHORT -> "короткие"
                                SortOption.UNDEFINED -> "неизвестный"
                            },
                            color = greyDark
                        )
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 200)
@Composable
fun SortDropdownPreview() {
    SortDropdown(
        options = listOf(
            SortOption.SHORT,
            SortOption.LONG,
            SortOption.HIGH_RATING,
            SortOption.CLOSER_TO_ME
        ),
        initialSelection = SortOption.CLOSER_TO_ME,
        onOptionSelected = { selectedOption ->
            println("Selected option: $selectedOption")
        },
        modifier = Modifier
    )
}

