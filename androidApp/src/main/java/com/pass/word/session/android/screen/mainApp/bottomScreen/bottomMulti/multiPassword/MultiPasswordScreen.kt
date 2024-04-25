package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.multiPassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.MultiPasswordScreenContent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenMultiPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenMultiPasswordEvent

@Composable
fun MultiPasswordScreen(component: ScreenMultiPasswordComponent) {
    val context = LocalContext.current
    val stateLoading by component.stateLoading.collectAsState()
    val statePassItemDisplay by component.statePassItemDisplay.collectAsState()
    val stateCallItem by component.stateCallItem.collectAsState()
    val stateSelectedTypeStorage by component.stateSelectedTypeStorage.collectAsState()
    val stateVisibleStatusBar by component.stateVisibleStatusBar.collectAsState()

    LaunchedEffect(stateCallItem) {
        if (stateCallItem) {
            component.onEvent(ScreenMultiPasswordEvent.ReadBdItem(DriverFactory(context)))
        }
        component.onEvent(ScreenMultiPasswordEvent.ReadCashPass(DriverFactory(context)))
    }


    MultiPasswordScreenContent(
        stateLoading = stateLoading,
        statePassItemDisplay = statePassItemDisplay,
        stateSelectedTypeStorage = stateSelectedTypeStorage,
        stateVisibleStatusBar = stateVisibleStatusBar,
        driverFactory = DriverFactory(context),
        eventComponentDispatch = {
            component.onEvent(it)
        }
    )
}