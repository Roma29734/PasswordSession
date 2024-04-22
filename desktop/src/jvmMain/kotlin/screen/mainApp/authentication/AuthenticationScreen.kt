package screen.mainApp.authentication

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.navigation.screen.mainApp.authentication.AuthenticationScreenContent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthStateEvent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.utilits.checkUseBiometric
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AuthenticationScreen(component: ScreenAuthenticationComponent) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val passItem: String by component.passItem.subscribeAsState()
    val passItemToCodeItem = remember { mutableStateOf("") }



    LaunchedEffect(passItem) {
        when (passItem.length) {
            1 -> {
                passItemToCodeItem.value = "•"
            }

            2 -> {
                passItemToCodeItem.value = "••"
            }

            3 -> {
                passItemToCodeItem.value = "•••"
            }

            4 -> {
                if (passItem[0] == '!') {
                    passItemToCodeItem.value = passItem
                } else {
                    passItemToCodeItem.value = "••••"
                }
            }
        }
    }



    DisposableEffect(component) {
        val cancel = component.showSnackBarDispatcher.subscribe {
            scope.launch {
                passItemToCodeItem.value = "----"
                snackBarHostState.showSnackbar(it)
                passItemToCodeItem.value = ""
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
            passItem = passItemToCodeItem.value,
            context = null,
            eventComponentDispatch = {
                component.event(it)
            },
            handlerBtnFinger = null
        )
    }
}