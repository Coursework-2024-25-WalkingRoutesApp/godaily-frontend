package ru.hse.coursework.godaily.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.coursework.godaily.ui.components.atoms.VariableMedium
import ru.hse.coursework.godaily.ui.components.molecules.Back
import ru.hse.coursework.godaily.ui.components.molecules.Exit
import ru.hse.coursework.godaily.ui.components.molecules.HeaderWithBackground
import ru.hse.coursework.godaily.ui.theme.purpleRoutes

@Composable
fun AboutUsScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 65.dp)
        ) {
            Box {
                HeaderWithBackground(header = "О программе")
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Back(
                        onClick = { navController.popBackStack() }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(15.dp))
                VariableMedium(text = "Связаться с разработчиками", fontSize = 20.sp)
                Spacer(Modifier.height(20.dp))
                ClickableTextLink(
                    text = "yuekukhtina@edu.hse.ru",
                    url = "mailto:yuekukhtina@edu.hse.ru",
                    onClick = ::openLink
                )
                Spacer(Modifier.height(10.dp))
                ClickableTextLink(
                    text = "aevsyukov_1@edu.hse.ru",
                    url = "mailto:aevsyukov_1@edu.hse.ru",
                    onClick = ::openLink
                )
                Spacer(Modifier.height(15.dp))
                VariableMedium(text = "Условия использования Яндекс карт", fontSize = 20.sp)
                Spacer(Modifier.height(20.dp))
                ClickableTextLink(
                    text = "https://yandex.ru/legal/maps_api/",
                    url = "https://yandex.ru/legal/maps_api/",
                    onClick = ::openLink
                )

                Spacer(Modifier.height(30.dp))
                Exit(onClick = { viewModel.exit() })
            }


        }
    }
}

@Composable
fun ClickableTextLink(text: String, url: String, onClick: (String) -> Unit) {
    val annotatedString = buildAnnotatedString {
        pushStyle(
            SpanStyle(
                color = purpleRoutes,
                textDecoration = TextDecoration.Underline,
                fontSize = 15.sp
            )
        )
        append(text)
        addStringAnnotation(tag = "URL", annotation = url, start = 0, end = text.length)
        pop()
    }

    BasicText(
        text = annotatedString,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick(url) }
    )
}
