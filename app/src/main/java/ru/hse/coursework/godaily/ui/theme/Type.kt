package ru.hse.coursework.godaily.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.hse.coursework.godaily.R

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Light),
    Font(R.font.roboto_medium, FontWeight.Medium),
    //Font(R.font.roboto_bold, FontWeight.Bold)
)


val headerBig = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 28.sp,
    color = Color.Black
)

val headerMedium = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp,
    color = Color.Black
)

val routeNameBig = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 32.sp,
    color = Color.Black
)

val routeNameSmall = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    color = Color.Black
)

val routeDestinations = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 18.sp,
    color = Color.Black
)

val routeDescription = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 15.sp,
    color = Color.Black
)