package ru.hse.coursework.godaily.ui.components.superorganisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.coursework.godaily.R
import ru.hse.coursework.godaily.ui.components.molecules.BackInstructionButton
import ru.hse.coursework.godaily.ui.components.molecules.NextStepButton
import ru.hse.coursework.godaily.ui.theme.lime

@Composable
fun Tutorial(
    onFinish: () -> Unit
) {
    val currentStep = remember { mutableStateOf(0) }
    val totalSteps = 6

    val imageResources = listOf(
        R.drawable.instruction_1,
        R.drawable.instruction_2,
        R.drawable.instruction_3,
        R.drawable.instruction_4,
        R.drawable.instruction_5,
        R.drawable.instruction_6
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = imageResources[currentStep.value]),
            contentDescription = "Шаг ${currentStep.value + 1}",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = 1.2f,
                    scaleY = 1.2f,
                    clip = false
                )
                .offset(x = 0.dp, y = (30).dp),
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentStep.value < totalSteps - 1) {
                NextStepButton(
                    onClick = { currentStep.value++ }
                )
            } else {
                NextStepButton(
                    text = "Понятно",
                    onClick = onFinish,
                    containerColor = lime
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            if (currentStep.value > 0) {
                BackInstructionButton(
                    onClick = { currentStep.value-- }
                )
            } else {
                Spacer(modifier = Modifier.height(17.dp))
            }
        }
    }
}

@Preview
@Composable
fun TutorialPreview() {
    Tutorial({})
}