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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.molecules.SearchBar
import ru.hse.coursework.godaily.ui.components.molecules.SortDropdown
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun SearchToolbar(
    searchValue: MutableState<String>,
    onSearchValueChange: (String) -> Unit,
    filterIconClick: () -> Unit,
    sortClick: () -> Unit,
    chosenSortOption: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(90.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        SearchBar(
            text = searchValue,
            placeholder = "Поиск",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onSearchValueChange
        )

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filters",
                tint = greyDark,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { filterIconClick() }
            )

            Spacer(modifier = Modifier.weight(1f))

            SortDropdown(
                onSortClick = sortClick,
                chosenSortOption = chosenSortOption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchAndFilterBarPreview() {
    val searchValue = mutableStateOf("")
    SearchToolbar(
        searchValue = searchValue,
        onSearchValueChange = { searchValue.value = it },
        filterIconClick = { println("SortOption button clicked") },
        sortClick = {},
        chosenSortOption = mutableStateOf("ближе ко мне")
    )
}
