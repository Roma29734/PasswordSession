package com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun EnterSeedPhraseScreen(component: ScreenEnterSeedPhraseComponent) {

    val passEnterState by component.stateEnableButtonNext.collectAsState()
    val stateSeedText by component.stateSeedText.collectAsState().value
    val stateOpenDialogLoading by component.stateOpenDialogLoading.collectAsState()
    val stateLoadingAlert by component.stateLoadingAlert.collectAsState()


    EnterSeedPhraseScreenContent(
        passEnterState = passEnterState,
                stateSeedText = stateSeedText,
        stateOpenDialogLoading= stateOpenDialogLoading,
        stateLoadingAlert = stateLoadingAlert,
        eventDispatch = {
            component.onEvent(it)
        }
    )
}

