package com.pass.word.session.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

expect fun acmeTypography(): Typography

@Composable
fun MyCustomAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = acmeTypography()

    MaterialTheme(
        typography = typography,
        content = content
    )
}

data class CustomColor(
    val grayLight: Color = Color(0xFF5D5D61),
    val brandBlueLight: Color = Color(0xFF0C98FF),
    val accentDark: Color = Color(0xFF1C1C1E),
    val brandRedMain: Color = Color(0xFFFF4D3F),
    val brandGreen: Color = Color(0xFF49F78E)
)