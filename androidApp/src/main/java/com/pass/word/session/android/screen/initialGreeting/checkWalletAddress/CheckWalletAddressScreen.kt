package com.pass.word.session.android.screen.initialGreeting.checkWalletAddress

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.main.initialGreeting.screenCheckWalletAddress.ScreenCheckWalletAddressComponent

@Composable
fun CheckWalletAddressScreen(component: ScreenCheckWalletAddressComponent) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UpBarButtonBack(onBackHandler = {
            component.onBack()
        })

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = component.itemWalletAddress, style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    .clickable {
                        // display term of use
                    },
                text = "Make sure that this is your wallet address, it must have at least 1.5 ton on it for the application to work",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
        MainComponentButton("Continue", true) {
            component.onNext()
        }
    }

}