package com.pass.word.session.android.screen.mainApp.editScreen

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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.viewComponent.CustomErrorDialog
import com.pass.word.session.android.screen.viewComponent.CustomLoadingDialog
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.OutlineInputText
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.edit.ScreenEditComponent
import com.pass.word.session.navigation.screen.mainApp.edit.ScreenEditEvent
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditScreen(component: ScreenEditComponent) {

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


        if (stateOpenDialogLoading is StateBasicDialog.Show) {
            Dialog(onDismissRequest = { stateOpenDialogLoading is StateBasicDialog.Show }) {
                CustomLoadingDialog()
            }
        }

        if (stateOpenDialogLoading is StateBasicDialog.Error) {
            Dialog(onDismissRequest = { stateOpenDialogLoading is StateBasicDialog.Error }) {
                CustomErrorDialog(
                    textTitle = "An error has occurred",
                    textSubTitle = "An error occurred during the execution of the request. try again later",
                    textButton = "close",
                    handlerButton = {
                        component.onEvent(ScreenEditEvent.CloseAllAlert)
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                UpBarButtonBack(onBackHandler = {
                    component.onEvent(ScreenEditEvent.ClickButtonBack)
                })
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp),
                    text = "Edit",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlineInputText(
                    textInTitle = "Title",
                    outText = textTitle,
                    onValueChangeHandler = { component.onEvent(ScreenEditEvent.UpdateTextTitle(it)) },
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
                            ScreenEditEvent.UpdateTextEmailORUserName(
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
                            ScreenEditEvent.UpdateTextPassword(
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
                        component.onEvent(ScreenEditEvent.UpdateTextUrl(it))
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
                            ScreenEditEvent.UpdateTextDescriptions(
                                it
                            )
                        )
                    },
                    focusRequester = focusRequesterDescriptions,
                    onNextHandler = {
                        focusManager.clearFocus()
                    }, keyboardType = KeyboardType.Text
                )
            }

            MainComponentButton(
                text = "apply changes",
                true
            ) {
                component.onEvent(ScreenEditEvent.ClickButtonUpdate(DriverFactory(context)))
            }

        }
    }
}