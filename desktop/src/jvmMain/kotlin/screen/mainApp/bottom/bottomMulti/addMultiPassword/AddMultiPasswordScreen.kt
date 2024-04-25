package screen.mainApp.bottom.bottomMulti.addMultiPassword

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.AddMultiPasswordScreenContent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.ScreenAddMultiPasswordComponent
import kotlinx.coroutines.launch

@Composable
fun AddMultiPasswordScreen(component: ScreenAddMultiPasswordComponent) {

    val textTitle: String by component.textTitle.subscribeAsState()
    val textEmailORUserName: String by component.textEmailOrUserName.subscribeAsState()
    val textPassword: String by component.textPassword.subscribeAsState()
    val textUrl: String by component.textUrl.subscribeAsState()
    val textDescriptions: String by component.textDescriptions.subscribeAsState()

    val stateOpenDialogLoading by component.stateOpenDialogChoseType.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }
        onDispose {
            // Отписка при уничтожении экрана
            cancel()
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        AddMultiPasswordScreenContent(
            textTitle = textTitle,
            textEmailORUserName = textEmailORUserName,
            textPassword = textPassword,
            textUrl = textUrl,
            textDescriptions= textDescriptions,
            stateOpenDialogLoading = stateOpenDialogLoading,
            driverFactory = DriverFactory(),
            eventComponentDispatch =  {
                component.onEvent(it)
            }
        )
    }
}