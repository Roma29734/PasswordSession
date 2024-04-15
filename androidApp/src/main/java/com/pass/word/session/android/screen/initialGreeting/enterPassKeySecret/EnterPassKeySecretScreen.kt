package com.pass.word.session.android.screen.initialGreeting.enterPassKeySecret

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret.EnterPassKeySecretScreenContent
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret.ScreenEnterPassKeySecretComponent

@Composable
fun EnterPassKeySecretScreen(component: ScreenEnterPassKeySecretComponent) {

    val statePassKeySecret by component.statePassKeySecret.collectAsState()
    val stateEnableButtonNext by component.stateEnableButtonNext.collectAsState()
    val stateLoading by component.stateLoading.collectAsState()
    val warningText by component.warningText.collectAsState()


    EnterPassKeySecretScreenContent(
        statePassKeySecret=  statePassKeySecret,
        stateEnableButtonNext = stateEnableButtonNext,
        stateLoading = stateLoading,
        warningText = warningText
    ) { component.onEvent(it) }
}