package screen.mainApp.detail

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.detail.DetailScreenContent
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailComponent
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(component: ScreenDetailComponent) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val stateOpenAlertDialog: Boolean by component.stateOpenAlertDialog.subscribeAsState()
    val stateLoading by component.stateOpenDialogChoseType.collectAsState()

    val itemModel: PasswordItemModel by component.passwordItem.subscribeAsState()

    component.getOneItem(DriverFactory())

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        DetailScreenContent(
            clipboardManager = clipboardManager,
            stateOpenAlertDialog = stateOpenAlertDialog,
            stateLoading = stateLoading,
            itemModel = itemModel,
            driverFactory = DriverFactory(),
            snackBarHostHandler = {
                scope.launch {
                    snackbarHostState.showSnackbar(it)
                }
            },
            eventComponentDispatch = {
                component.onEvent(it)
            }
        )
    }
}

