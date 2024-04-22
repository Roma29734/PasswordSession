package screen.mainApp.enterPass

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
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.EnterPassScreenContent
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.ScreenEnterPassComponent
import kotlinx.coroutines.launch

@Composable
fun EnterPassScreen(component: ScreenEnterPassComponent) {
    val passItem: String by component.passItem.subscribeAsState()
    val passEnterState by component.stateEnterPass.collectAsState()
    val scope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }

    DisposableEffect(component) {
        val listenerSnackBarShow: (String) -> Unit = {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }
        component.subscribeListenerSnackBar(listenerSnackBarShow)

        onDispose {
            // Отписка при уничтожении экрана
            component.unsubscribeListenerSnackBar(listenerSnackBarShow)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        EnterPassScreenContent(
            passItem = passItem,
            passEnterState = passEnterState,
            context = null,
            eventComponentDispatch = {
                component.onEvent(it)
            }
        )

    }
}