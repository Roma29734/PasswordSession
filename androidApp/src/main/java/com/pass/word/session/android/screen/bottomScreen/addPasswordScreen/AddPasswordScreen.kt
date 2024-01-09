package com.pass.word.session.android.screen.bottomScreen.addPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent.ScreenAddPasswordComponent

@Composable
fun AppPasswordScreen(component: ScreenAddPasswordComponent) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
            text = "Add New Password",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}