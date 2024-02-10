package com.example.kinopoisk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.kinopoisk.R


private val Robot = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Robot,
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.sp,
        platformStyle = PlatformTextStyle(false),
    ),
    titleSmall = TextStyle(
        fontFamily = Robot,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(false),
    ),
    bodyLarge = TextStyle(
        fontFamily = Robot,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        platformStyle = PlatformTextStyle(false),
    ),
    bodyMedium = TextStyle(
        fontFamily = Robot,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(false),
    ),
    labelLarge = TextStyle(
        fontFamily = Robot,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(false),
    ),
)