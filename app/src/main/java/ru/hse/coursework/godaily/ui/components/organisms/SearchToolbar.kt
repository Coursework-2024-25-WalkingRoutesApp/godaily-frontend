package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.molecules.SearchBar
import ru.hse.coursework.godaily.ui.components.molecules.SortDropdown
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun SearchAndFilterBar(
    searchValue: TextFieldValue,
    onSearchValueChange: (TextFieldValue) -> Unit,
    filterIconClick: () -> Unit,
    sortOptions: List<String>,
    onSortOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(341.dp)
            .height(90.dp)
            .padding(8.dp)
    ) {
        SearchBar(
            value = TextFieldValue(""),
            onValueChange = { newValue -> onSearchValueChange(newValue) },
            placeholder = "Поиск",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter), // Замени на свой ID иконки
                contentDescription = "Filters",
                tint = greyDark,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { filterIconClick() }
            )

            Spacer(modifier = Modifier.weight(1f))

            SortDropdown(
                options = sortOptions,
                initialSelection = sortOptions.first(),
                onOptionSelected = onSortOptionSelected,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchAndFilterBarPreview() {
    SearchAndFilterBar(
        searchValue = TextFieldValue(""),
        onSearchValueChange = { println("Search: $it") },
        filterIconClick = { println("Filter button clicked") },
        sortOptions = listOf("Длинные", "Короткие", "Ближе ко мне", "С высоким рейтингом"),
        onSortOptionSelected = { selectedOption -> println("Sort selected: $selectedOption") },
    )
}
