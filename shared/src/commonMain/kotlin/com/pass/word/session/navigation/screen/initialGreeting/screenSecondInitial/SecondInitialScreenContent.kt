package com.pass.word.session.navigation.screen.initialGreeting.screenSecondInitial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.CustomColor

@Composable
fun SecondInitialScreenContent(
    eventComponentDispatch: () -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(128.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = "Please note that the application does not store anything on the servers, so if you forget the password or delete the application, all data will be lost. All security belongs to you!",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(64.dp))
        Button(
            onClick = {
                eventComponentDispatch()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(CustomColor().brandBlueLight)
        ) {
            Text(
                text = "continue",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
        }
    }
}