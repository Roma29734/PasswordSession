package com.pass.word.session.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp


actual fun acmeTypography(): Typography = typography

val poppinsFont = FontFamily(
    Font("resources/font/poppins_black.ttf", FontWeight.Black),
    Font("resources/font/poppins_bold.ttf", FontWeight.Bold),
    Font("resources/font/poppins_extra_bold.ttf", FontWeight.ExtraBold),
    Font("resources/font/poppins_semi_bold.ttf", FontWeight.SemiBold),
    Font("resources/font/poppins_medium.ttf", FontWeight.Medium),
)
//
val typography = Typography(
    bodyMedium = TextStyle(
//        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
//        fontFamily = poppinsFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp
    ),
    displayMedium = TextStyle(
        fontSize = 16.sp,
//        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
    ),
    displaySmall = TextStyle(
        fontSize = 14.sp,
//        fontFamily = poppinsFont,
        fontWeight = FontWeight.SemiBold
    ),
    displayLarge = TextStyle(
        fontSize = 22.sp,
//        fontFamily = poppinsFont,
        fontWeight = FontWeight.SemiBold
    )
)
