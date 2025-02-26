package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.PublishButton
import ru.hse.coursework.godaily.ui.theme.greyDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteStartBox(
    onStartClick: () -> Unit,
    routeTitle: String,
    startPoint: String
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 200.dp,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                VariableMedium(text = routeTitle, fontSize = 26.sp)
                VariableMedium(
                    text = startPoint,
                    fontSize = 22.sp,
                    fontColor = greyDark,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    ) {}

    Box(Modifier.fillMaxSize()) {
        PublishButton(
            onClick = onStartClick,
            text = "Начать прохождение",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(217.dp)
                .padding(bottom = 16.dp)
        )
    }
}

@Preview
@Composable
fun RouteStartBoxPreview() {
    RouteStartBox(
        onStartClick = {},
        routeTitle = "Измайловский кремль",
        startPoint = "Дворец культуры"
    )
}
