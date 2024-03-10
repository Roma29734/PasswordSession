package com.pass.word.session.android.screen.mainApp.enterPassScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.viewComponent.BoxItemCode
import com.pass.word.session.android.screen.viewComponent.ButtonNumber
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.ScreenEnterPassComponent
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.ScreenEnterPassEvent
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EnterPassScreen(component: ScreenEnterPassComponent) {


    val passItem: String by component.passItem.subscribeAsState()
    val passEnterState by component.stateEnterPass.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                UpBarButtonBack(onBackHandler = {
                    component.onEvent(ScreenEnterPassEvent.ClickButtonBack)
                })

                Spacer(modifier = Modifier.size(48.dp))

                if (!passEnterState) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Enter the password to log in to the application. Attention, if you forget your password, you will not be able to restore it",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Confirm the password you entered earlier",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
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
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it,
                                context
                            )
                        )
                    }
                    ButtonNumber(2) {
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
                            )
                        )
                    }
                    ButtonNumber(3) {
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
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
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
                            )
                        )
                    }
                    ButtonNumber(5) {
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
                            )
                        )
                    }
                    ButtonNumber(6) {
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
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
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
                            )
                        )
                    }
                    ButtonNumber(8) {
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
                            )
                        )
                    }
                    ButtonNumber(9) {
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
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
                        component.onEvent(
                            ScreenEnterPassEvent.StateUpdatePassItem(
                                it, context
                            )
                        )
                    }
                    Box(
                        Modifier
                            .size(64.dp),
                    )
                }
            }
        }
    }
}