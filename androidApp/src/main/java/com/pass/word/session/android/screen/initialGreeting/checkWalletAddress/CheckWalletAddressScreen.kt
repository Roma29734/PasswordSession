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
import com.pass.word.session.navigation.screen.initialGreeting.screenCheckWalletAddress.CheckWalletAddressScreenContent
import com.pass.word.session.navigation.screen.initialGreeting.screenCheckWalletAddress.ScreenCheckWalletAddressComponent

@Composable
fun CheckWalletAddressScreen(component: ScreenCheckWalletAddressComponent) {
    CheckWalletAddressScreenContent(
        handlerBackNav = {
            component.onBack()
        },
        handlerNextNav = {
            component.onNext()
        },
        txAddress = component.itemWalletAddress
    )

}