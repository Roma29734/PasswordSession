package com.pass.word.session.android.screen.seedPhraseSettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.ScreenSeedPhraseSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.SeedPhraseSettingsScreenContent

@Composable
fun SeedPhraseSettingsScreen(component: ScreenSeedPhraseSettingsComponent) {

    val seedPhraseState by component.seedPhraseState.collectAsState()
    val stateVisibleDialog by component.stateVisibleDialog.collectAsState()
    val context = LocalContext.current

    SeedPhraseSettingsScreenContent(
        seedPhraseState = seedPhraseState,
        stateVisibleDialog = stateVisibleDialog,
        driverFactory = DriverFactory(context),
        eventComponentDispatch = {
            component.onEvent(it)
        }
    )
}