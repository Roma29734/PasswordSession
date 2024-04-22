package com.pass.word.session.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pass.word.session.R

actual fun acmeTypography(): AcmeTypographyResponse =
    AcmeTypographyResponse(small = typographySmall, medium = typographyMedium)

val poppinsFont = FontFamily(
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_medium, FontWeight.Medium),
)

val typographyMedium = Typography(
    bodyMedium = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp
    ),
    displayMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
    ),
    displaySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = poppinsFont,
        fontWeight = FontWeight.SemiBold
    ),
    displayLarge = TextStyle(
        fontSize = 22.sp,
        fontFamily = poppinsFont,
        fontWeight = FontWeight.SemiBold
    )
)

val typographySmall = Typography(
    bodyMedium = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    displayMedium = TextStyle(
        fontSize = 12.sp,
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
    ),
    displaySmall = TextStyle(
        fontSize = 10.sp,
        fontFamily = poppinsFont,
        fontWeight = FontWeight.SemiBold
    ),
    displayLarge = TextStyle(
        fontSize = 18.sp,
        fontFamily = poppinsFont,
        fontWeight = FontWeight.SemiBold
    )
)