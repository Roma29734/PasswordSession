package com.pass.word.session.android.screen.passKeySettings.primaryAction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.ScreenPrimaryKeyActionComponent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.ItemPhrase

@Composable
fun PrimaryActionScreen(component: ScreenPrimaryKeyActionComponent) {
    val passKeyPhraseState by component.passKeyPhraseState.collectAsState()

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



        Column {
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                text = "Attention, this is your secret phrase that encrypts your passwords, do not give it to anyone",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Box(modifier = Modifier.size(64.dp))
            ItemPhrase(passKeyPhraseState)
        }


        MainComponentButton(
            "Edit key phrase",
            true,
            colorButton = CustomColor().brandRedMain
        ) {
            component.onNext()
        }
    }
}