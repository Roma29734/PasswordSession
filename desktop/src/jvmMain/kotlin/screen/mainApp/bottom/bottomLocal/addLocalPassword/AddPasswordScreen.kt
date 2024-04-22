package screen.mainApp.bottom.bottomLocal.addLocalPassword

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent.AddPasswordScreenContent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent.ScreenAddPasswordComponent
import kotlinx.coroutines.launch

@Composable
fun AppPasswordScreen(component: ScreenAddPasswordComponent) {

    val textTitle: String by component.textTitle.subscribeAsState()
    val textEmailORUserName: String by component.textEmailOrUserName.subscribeAsState()
    val textPassword: String by component.textPassword.subscribeAsState()
    val textUrl: String by component.textUrl.subscribeAsState()
    val textDescriptions: String by component.textDescriptions.subscribeAsState()


    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    DisposableEffect(component) {
        val listenerPassCreated: (message: String, complete: Boolean) -> Unit = { msg, complete ->
            scope.launch {
                snackBarHostState.showSnackbar(msg)
            }
        }
        component.subscribeListenerPassCreate(listenerPassCreated)

        onDispose {
            // Отписка при уничтожении экрана
            component.unsubscribeListenerPassCreate(listenerPassCreated)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        AddPasswordScreenContent(
            textTitle = textTitle,
            textEmailORUserName =textEmailORUserName,
            textPassword = textPassword,
            textUrl = textUrl,
            textDescriptions = textDescriptions,
            eventComponentDispatch = {
                component.onEvent(it)
            },
            driverFactory = DriverFactory()
        )
    }
}