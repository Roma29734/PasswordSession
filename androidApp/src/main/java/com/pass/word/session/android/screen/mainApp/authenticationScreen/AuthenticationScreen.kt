package com.pass.word.session.android.screen.mainApp.authenticationScreen

import Img.MyIconPack
import Img.myiconpack.IcLogoGitHub
import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.BoxItemCode
import com.pass.word.session.android.screen.viewComponent.ButtonNumber
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthStateEvent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.utilits.checkUseBiometric
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AuthenticationScreen(component: ScreenAuthenticationComponent) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val passItem: String by component.passItem.subscribeAsState()

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            Log.d("tagLogger", "Вызывается sideEffect")
        }
    }

    component.passItem.observe {
        Log.d("authScreen", "item $it")
    }

    DisposableEffect(component) {
        val listenerSnackBarShow: (String) -> Unit = {
            scope.launch {
                snackBarHostState.showSnackbar(it)
                println("setUp state clic to false")
                component.clickState = false
            }
        }
        component.subscribeListenerSnackBar(listenerSnackBarShow)

        onDispose {
            // Отписка при уничтожении экрана
            component.unsubscribeListenerSnackBar(listenerSnackBarShow)
        }
    }

    DisposableEffect(Unit) {

        checkUseBiometric(context = context, onAction = { successState: Boolean, message: String? ->
            component.event(
                ScreenAuthStateEvent.ReactionToFirstBiometrics(
                    successState = successState,
                    errorMessage = message,
                    context = context
                )
            )
        })

        onDispose {}
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

            Text(
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
                            context
                        )
                    )
                }
                ButtonNumber(2) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            context
                        )
                    )
                }
                ButtonNumber(3) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            context
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
                            context
                        )
                    )
                }
                ButtonNumber(5) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            context
                        )
                    )
                }
                ButtonNumber(6) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            context
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
                            context
                        )
                    )
                }
                ButtonNumber(8) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            context
                        )
                    )
                }
                ButtonNumber(9) {
                    component.event(
                        ScreenAuthStateEvent.StateUpdatePassItem(
                            it.toString(),
                            context
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
                            it.toString(), context
                        )
                    )
                }
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(start = 16.dp)
                        .clickable {
                            checkUseBiometric(context, onAction = { successState, message ->
                                component.event(
                                    ScreenAuthStateEvent.ReactionToFollowingBiometrics(
                                        successState = successState,
                                        errorMessage = message,
                                        context = context
                                    )
                                )
                            })
                        },
                    painter = painterResource(id = R.drawable.ic_finger_print),
                    contentDescription = "fingerPrintIco",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}