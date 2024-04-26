package com.pass.word.session.navigation.screen.initialGreeting.screenFirstInitial

import Img.MyIconPack
import Img.myiconpack.IcSecurityLock
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.CustomColor

@Composable
fun FirstInititalScreenContent(
    onClickHandlerBtnNxt: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background).verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(128.dp),
            imageVector = MyIconPack.IcSecurityLock,
            contentDescription = "ic_security_lock",
            colorFilter = ColorFilter.tint(CustomColor().brandBlueLight)
        )
        Spacer(modifier = Modifier.size(64.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = "Welcome to the password session, click on the button to continue",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(64.dp))
        Button(
            onClick = {
                onClickHandlerBtnNxt()
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