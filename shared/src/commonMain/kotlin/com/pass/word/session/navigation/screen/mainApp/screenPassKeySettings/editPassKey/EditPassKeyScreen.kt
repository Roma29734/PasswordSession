package com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.editPassKey

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun EditPassKeyScreen(component: ScreenEditPassKeyComponent) {

    val statePassKeySecret by component.statePassKeySecret.collectAsState()
    val stateEnableButtonNext by component.stateEnableButtonNext.collectAsState()

    val warningText by component.warningText.collectAsState()
    val stateShowedDialog by component.stateShowedDialog.collectAsState()

    EditPassKeyScreenContent(
        statePassKeySecret = statePassKeySecret,
        stateEnableButtonNext = stateEnableButtonNext,
        warningText = warningText,
        stateShowedDialog = stateShowedDialog,
        eventComponentDispatch = {component.onEvent(it)}
    )
}