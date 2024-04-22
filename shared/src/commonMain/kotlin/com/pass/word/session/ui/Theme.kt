package com.pass.word.session.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AcmeTypographyResponse(
    val small: Typography,
    val medium: Typography
)

expect fun acmeTypography(): AcmeTypographyResponse

val colorScheme = darkColorScheme(
    background = CustomColor().mainBlue
)

@Composable
fun MyCustomAppTheme(
    screenWidthDp: Dp,
    screenHeightDp: Dp,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    println("screeenWidthDp - $screenWidthDp")
    if(screenWidthDp >= 380.dp) {
        val typography = acmeTypography().medium
        MaterialTheme(
            typography = typography,
            content = content,
            colorScheme = colorScheme
        )
    } else {
        val typography = acmeTypography().small
        MaterialTheme(
            typography = typography,
            content = content,
            colorScheme = colorScheme
        )
    }


}

data class CustomColor(
    val grayLight: Color = Color(0xFF566171),
    val brandBlueLight: Color = Color(0xFF0C98FF),
    val accentDark: Color = Color(0xFF1C1C1E),
    val brandRedMain: Color = Color(0xFFFF4D3F),
    val brandGreen: Color = Color(0xFF49F78E),
    val mainBlue: Color = Color(0xFF10151F)
)