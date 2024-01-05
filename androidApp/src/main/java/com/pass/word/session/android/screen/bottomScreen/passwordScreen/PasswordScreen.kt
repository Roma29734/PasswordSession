package com.pass.word.session.android.screen.bottomScreen.passwordScreen

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pass.word.session.navigation.screen.bottom.screenPasswordComponent.ScreenPasswordComponent

@Composable
fun PasswordScreen(component: ScreenPasswordComponent) {
    Text(modifier = Modifier, text = "password", color = MaterialTheme.colorScheme.primary)
}