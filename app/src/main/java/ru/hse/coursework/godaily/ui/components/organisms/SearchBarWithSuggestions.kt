package ru.hse.coursework.godaily.ui.components.organisms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yandex.mapkit.search.SuggestItem
import ru.hse.coursework.godaily.ui.theme.greyDark

@Composable
fun SearchBarWithSuggestions(
    query: MutableState<String>,
    suggestions: SnapshotStateList<SuggestItem>,
    onValueChange: (String) -> Unit,
    onSuggestionClick: (SuggestItem) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusRequester.requestFocus()
                        }
                    )
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(rememberScrollState())
                ) {
                    BasicTextField(
                        value = query.value,
                        onValueChange = {
                            query.value = it
                            isFocused = it.isNotEmpty()
                            onValueChange(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        maxLines = 1,
                        singleLine = true
                    )
                }

                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_search),
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                        .clickable {
                            onSearchClick()
                            isFocused = false
                        }
                )
            }
        }

        AnimatedVisibility(visible = isFocused && suggestions.isNotEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shadowElevation = 4.dp,
                color = Color.White
            ) {
                Column {
                    suggestions.forEach { suggestion ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    query.value = suggestion.displayText.toString()
                                    isFocused = false
                                    onSuggestionClick(suggestion)
                                }
                                .padding(12.dp)
                        ) {
                            Text(
                                text = suggestion.title.text,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                fontSize = 16.sp
                            )
                            if (suggestion.subtitle != null) {
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = suggestion.subtitle!!.text,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    fontSize = 12.sp,
                                    color = greyDark
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBarWithSuggestions(
        query = mutableStateOf("vjnkjnk"),
        suggestions = mutableStateListOf(),
        onValueChange = {},
        onSearchClick = {},
        onSuggestionClick = {}
    )
}
