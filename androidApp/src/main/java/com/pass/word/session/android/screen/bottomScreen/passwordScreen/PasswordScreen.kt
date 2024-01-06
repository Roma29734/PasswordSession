package com.pass.word.session.android.screen.bottomScreen.passwordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.CustomColor
import com.pass.word.session.navigation.screen.bottom.screenPasswordComponent.ScreenPasswordComponent

@Composable
fun PasswordScreen(component: ScreenPasswordComponent) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(CustomColor().accentDark)) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp),
            text = "Password",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        
        LazyColumn {

        }
        
    }
}