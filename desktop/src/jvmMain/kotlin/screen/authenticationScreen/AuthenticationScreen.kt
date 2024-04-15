package screen.authenticationScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthStateEvent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.ui.viewComponent.BoxItemCode
import com.pass.word.session.ui.viewComponent.ButtonNumber
import com.pass.word.session.utilits.checkUseBiometric
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
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            androidx.compose.material3.Text(
                text = "Enter your authentication code",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BoxItemCode(if (passItem.isNotEmpty()) "•" else "")
                BoxItemCode(if (passItem.length >= 2) "•" else "")
                BoxItemCode(if (passItem.length >= 3) "•" else "")
                BoxItemCode(if (passItem.length >= 4) "•" else "")
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ButtonNumber(1) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
                ButtonNumber(2) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
                ButtonNumber(3) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ButtonNumber(4) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
                ButtonNumber(5) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
                ButtonNumber(6) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ButtonNumber(7) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
                ButtonNumber(8) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
                ButtonNumber(9) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            null
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Box(
                    Modifier
                        .size(64.dp),
                )
                ButtonNumber(textButton = 0) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(), null
                        )
                    )
                }
            }
        }
    }
}