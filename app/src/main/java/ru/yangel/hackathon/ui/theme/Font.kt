package ru.yangel.hackathon.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.yangel.hackathon.R

internal val FontLarge = 32.sp
internal val FontMedium = 24.sp
internal val FontNormal = 20.sp
internal val FontRegular = 18.sp
internal val FontSmall = 16.sp

internal val FontScheduleTitle = 23.sp

val Roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.SemiBold),
    Font(R.font.roboto_regular, FontWeight.Normal)
)

val Type24 = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    lineHeight = 28.sp
)

val Type20 = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 28.sp
)

val Type15 = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 24.sp
)


val Type15Search = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    color = Nevada,
    lineHeight = 24.sp
)

val Type13 = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 20.sp
)

val Type11 = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
    lineHeight = 16.sp
)

val Type10 = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    lineHeight = 20.sp
)

val RobotoFlex = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_regular, FontWeight.Normal),
)