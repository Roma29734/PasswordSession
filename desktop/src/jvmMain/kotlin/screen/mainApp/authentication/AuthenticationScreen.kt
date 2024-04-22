package screen.mainApp.authentication

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.navigation.screen.mainApp.authentication.AuthenticationScreenContent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AuthenticationScreen(component: ScreenAuthenticationComponent) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val passItem: String by component.passItem.subscribeAsState()

    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                snackBarHostState.showSnackbar(it)
                println("setUp state clic to false")
                component.clickState = false
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
        AuthenticationScreenContent(
            passItem = passItem,
            context = null,
            eventComponentDispatch = {
                component.event(it)
            }
        )
    }
}