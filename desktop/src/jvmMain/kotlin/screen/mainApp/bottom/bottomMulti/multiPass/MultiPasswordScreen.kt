package screen.mainApp.bottom.bottomMulti.multiPass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.MultiPasswordScreenContent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenMultiPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenMultiPasswordEvent

@Composable
fun MultiPasswordScreen(component: ScreenMultiPasswordComponent) {
    val stateLoading by component.stateLoading.collectAsState()
    val statePassItemDisplay by component.statePassItemDisplay.collectAsState()
    val stateCallItem by component.stateCallItem.collectAsState()
    val stateSelectedTypeStorage by component.stateSelectedTypeStorage.collectAsState()
    val stateVisibleStatusBar by component.stateVisibleStatusBar.collectAsState()

    LaunchedEffect(stateCallItem) {
        if (stateCallItem) {
            component.onEvent(ScreenMultiPasswordEvent.ReadBdItem(DriverFactory()))
        }
        component.onEvent(ScreenMultiPasswordEvent.ReadCashPass(DriverFactory()))
    }


    MultiPasswordScreenContent(
        stateLoading = stateLoading,
        statePassItemDisplay = statePassItemDisplay,
        stateSelectedTypeStorage = stateSelectedTypeStorage,
        stateVisibleStatusBar = stateVisibleStatusBar,
        driverFactory = DriverFactory(),
        eventComponentDispatch = {
            component.onEvent(it)
        }
    )
}