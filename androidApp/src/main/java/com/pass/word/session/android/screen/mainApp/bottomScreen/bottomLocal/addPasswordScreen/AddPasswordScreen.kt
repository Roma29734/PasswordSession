package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.addPasswordScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.OutlineInputText
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent.ScreenAddPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent.ScreenAddPasswordStateEvent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun AppPasswordScreen(component: ScreenAddPasswordComponent) {

    val context = LocalContext.current

    val textTitle: String by component.textTitle.subscribeAsState()
    val textEmailORUserName: String by component.textEmailOrUserName.subscribeAsState()
    val textPassword: String by component.textPassword.subscribeAsState()
    val textUrl: String by component.textUrl.subscribeAsState()
    val textDescriptions: String by component.textDescriptions.subscribeAsState()

    val focusRequesterEmailUserName = remember { FocusRequester() }
    val focusRequesterTitle = remember { FocusRequester() }
    val focusRequesterPassword = remember { FocusRequester() }
    val focusRequesterUrl = remember { FocusRequester() }
    val focusRequesterDescriptions = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
                    text = "Add New Password",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )


                OutlineInputText(
                    textInTitle = "Title",
                    outText = textTitle,
                    onValueChangeHandler = {
                        component.onEvent(
                            ScreenAddPasswordStateEvent.UpdateTextTitle(
                                it
                            )
                        )
                    },
                    focusRequester = focusRequesterTitle,
                    onNextHandler = {
                        focusRequesterEmailUserName.requestFocus()
                    }, keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlineInputText(
                    textInTitle = "Email/UserName",
                    outText = textEmailORUserName,
                    onValueChangeHandler = {
                        component.onEvent(
                            ScreenAddPasswordStateEvent.UpdateTextEmailORUserName(
                                it
                            )
                        )
                    },
                    focusRequester = focusRequesterEmailUserName,
                    onNextHandler = {
                        focusRequesterPassword.requestFocus()
                    }, keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlineInputText(
                    textInTitle = "Password",
                    outText = textPassword,
                    onValueChangeHandler = {
                        component.onEvent(
                            ScreenAddPasswordStateEvent.UpdateTextPassword(
                                it
                            )
                        )
                    },
                    focusRequester = focusRequesterPassword,
                    onNextHandler = {
                        focusRequesterUrl.requestFocus()
                    }, keyboardType = KeyboardType.Password
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlineInputText(
                    textInTitle = "Url",
                    outText = textUrl,
                    onValueChangeHandler = {
                        component.onEvent(ScreenAddPasswordStateEvent.UpdateTextUrl(it))
                    },
                    focusRequester = focusRequesterUrl,
                    onNextHandler = {
                        focusRequesterDescriptions.requestFocus()
                    }, keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlineInputText(
                    textInTitle = "Descriptions",
                    outText = textDescriptions,
                    onValueChangeHandler = {
                        component.onEvent(
                            ScreenAddPasswordStateEvent.UpdateTextDescriptions(
                                it
                            )
                        )
                    },
                    focusRequester = focusRequesterDescriptions,
                    onNextHandler = {
                        focusManager.clearFocus()
//                        focusRequesterDescriptions.freeFocus()
                    }, keyboardType = KeyboardType.Text
                )

            }
            MainComponentButton(
                text = "add new password",
                true
            ) {
                component.onEvent(
                    ScreenAddPasswordStateEvent.ClickButtonAddNewState(
                        databaseDriverFactory = DriverFactory(context)
                    )
                )
            }
        }
    }
}