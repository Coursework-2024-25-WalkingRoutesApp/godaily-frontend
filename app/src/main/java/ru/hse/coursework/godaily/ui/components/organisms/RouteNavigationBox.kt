package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.PauseButton
import ru.hse.coursework.godaily.ui.components.molecules.PublishButton
import ru.hse.coursework.godaily.ui.theme.greyLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteNavigationBox(
    modifier: Modifier = Modifier,
    onNextPointClick: () -> Unit,
    onPauseClick: () -> Unit,
    onNextPointText: String = "К следующей точке",
    distanceToNextPoint: String = "0м",
    nextPointTitle: String = "Следующая точка",
    nextPointSubtitle: String = ""
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val descriptionColor by animateColorAsState(
        if (sheetState.currentValue == SheetValue.Expanded) Color.Black else greyLight,
        label = ""
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 200.dp,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(300.dp)
            ) {
                when (nextPointTitle) {
                    "Финиш" -> VariableMedium(
                        text = nextPointTitle,
                        fontSize = 21.sp,
                        modifier = Modifier.padding(top = 40.dp)
                    )

                    else -> {
                        PauseButton(
                            onClick = onPauseClick,
                            modifier = Modifier.align(Alignment.End)
                        )
                        VariableMedium(
                            text = "$nextPointTitle: ${distanceToNextPoint.ifEmpty { "0м" }}",
                            fontSize = 21.sp
                        )
                        nextPointSubtitle.takeIf { it.isNotBlank() }?.let {
                            ScrollableDescription(
                                description = it,
                                color = descriptionColor,
                                modifier = Modifier.padding(top = 5.dp, end = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    ) {}

    Box(Modifier.fillMaxSize()) {
        PublishButton(
            onClick = onNextPointClick,
            text = onNextPointText,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(217.dp)
                .padding(bottom = 16.dp)
        )
    }
}


@Preview
@Composable
fun RouteNavigationBoxPreview() {
    RouteNavigationBox(
        onNextPointClick = {},
        onPauseClick = {},
        nextPointTitle = "Пончиковая Пончиковая",
        nextPointSubtitle = "Кафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончикамиКафе с лучшими пончиками Кафе с лучшими пончиками Кафе с лучшими пончиками"
    )
}
