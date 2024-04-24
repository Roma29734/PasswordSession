package screen.mainApp.bottom.settings

import Img.MyIconPack
import Img.myiconpack.IcWarning
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsStateEvent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.SettingsScreenContent
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.ScreenSeedPhraseSettingsEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.CustomImageModel
import com.pass.word.session.ui.viewComponent.DialogLogOut
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.launch
import java.awt.Desktop
import java.net.URI

@Composable
fun SettingsScreen(component: ScreenSettingsComponent) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val itemSettingsList by component.itemSettingsList.collectAsState()
    val stateVisibleDialog by component.stateVisibleDialog.collectAsState()

    if (stateVisibleDialog is StateBasicDialog.Show) {
        Dialog(onDismissRequest = { stateVisibleDialog is StateBasicDialog.Hide }) {
            DialogLogOut(
                cancelHandler = {
                    component.onEvent(ScreenSettingsStateEvent.OnClickInDialogButton(false, DriverFactory()))
                },
                continueHandler = {
                    component.onEvent(ScreenSettingsStateEvent.OnClickInDialogButton(true, DriverFactory()))
                },
                textCancelButton = "Cancel",
                textContinueButton = "Continue",
                customImageModel = CustomImageModel(
                    painter = MyIconPack.IcWarning,
                    color = CustomColor().brandRedMain,
                    contentScale = ContentScale.Fit
                ),
                notifiText = "Dangerous",
                subTitleText = "when you click continue, you exit the application and all data will be erased forever, they will remain only in the blockchain",
                startTime = 15
            )
        }
    }

    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }
        onDispose {
            cancel()
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
                openUrlInBrowser(it)
            },
            mainComponentBtnHandler = {
                component.onEvent(ScreenSettingsStateEvent.ClickToButtonDownloadPass("adad", DriverFactory()))
            }
        )
    }
}

fun openUrlInBrowser(url: String) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(URI(url))
    }
}