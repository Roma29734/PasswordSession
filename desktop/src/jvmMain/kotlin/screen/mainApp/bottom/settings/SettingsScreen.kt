package screen.mainApp.bottom.settings

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsStateEvent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.SettingsScreenContent
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(component: ScreenSettingsComponent) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val itemSettingsList by component.itemSettingsList.collectAsState()

    DisposableEffect(component) {
        val listenerPassCreated: (message: String) -> Unit = { msg ->
            scope.launch {
                snackBarHostState.showSnackbar(msg)
            }
        }
        component.subscribeListenerToastPush(listenerPassCreated)

        onDispose {
            // Отписка при уничтожении экрана
            component.unsubscribeListenerToastPush(listenerPassCreated)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {

        SettingsScreenContent(
            itemSettingsList = itemSettingsList,
            eventComponentDispatch = {
                                     component.onEvent(it)
            },
            openUrlHandler = {

            },
            mainComponentBtnHandler = {
                component.onEvent(ScreenSettingsStateEvent.ClickToButtonDownloadPass("adad", DriverFactory()))
            }
        )
    }
}