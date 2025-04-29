package ru.hse.coursework.godaily.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.atoms.VariableLight
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun SortDropdown(
    onSortClick: () -> Unit,
    chosenSortOption: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(18.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .clickable { onSortClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            VariableLight(
                text = chosenSortOption.value,
                fontSize = 16.sp,
                fontColor = greyDark,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.arrow_dropdown),
                contentDescription = null,
                tint = greyDark,
                modifier = Modifier
                    .size(18.dp)
                    .padding(top = 3.dp)
            )
        }
    }
}


@Preview(showBackground = true, widthDp = 200)
@Composable
fun SortDropdownPreview() {
    SortDropdown(
        onSortClick = {},
        chosenSortOption = mutableStateOf("С высоким рейтингом"),
        modifier = Modifier
    )
}

