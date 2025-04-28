package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.CustomTextField
import ru.hse.coursework.godaily.ui.components.organisms.SelectableList
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun RouteInfoFields(
    title: MutableState<String>,
    description: MutableState<String>,
    startPoint: MutableState<String>,
    endPoint: MutableState<String>,
    chosenCategories: MutableState<Set<Int>>
) {
    CustomTextField(
        text = title,
        placeholder = "Название маршрута",
        isRequired = true,
        maxLength = 30
    )
    Spacer(modifier = Modifier.height(10.dp))
    CustomTextField(
        text = description,
        placeholder = "Описание маршрута",
        isRequired = false,
        maxLength = 1000
    )
    Spacer(modifier = Modifier.height(10.dp))
    CustomTextField(
        text = startPoint,
        placeholder = "Стартовая точка",
        isRequired = true,
        maxLength = 30
    )
    Spacer(modifier = Modifier.height(10.dp))
    CustomTextField(
        text = endPoint,
        placeholder = "Финишная точка",
        isRequired = true,
        maxLength = 30
    )

    Column(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        VariableMedium(
            text = "Выберите характеристики маршрута",
            fontSize = 14.sp,
            fontColor = greyDark
        )
        Spacer(modifier = Modifier.height(16.dp))
        SelectableList(selectedItems = chosenCategories)
    }
}