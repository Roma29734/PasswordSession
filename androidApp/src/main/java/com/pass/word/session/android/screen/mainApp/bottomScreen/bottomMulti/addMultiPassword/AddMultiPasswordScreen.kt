package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.addMultiPassword

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
import com.pass.word.session.android.screen.viewComponent.CustomChoseTypeDialog
import com.pass.word.session.android.screen.viewComponent.CustomErrorDialog
import com.pass.word.session.android.screen.viewComponent.CustomLoadingDialog
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.OutlineInputText
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.ScreenAddMultiPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.ScreenAddMultiPasswordEvent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.StateAddDialog
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddMultiPasswordScreen(component: ScreenAddMultiPasswordComponent) {

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (stateOpenDialogLoading is StateAddDialog.Show) {
                    Dialog(onDismissRequest = { stateOpenDialogLoading is StateAddDialog.Show }) {
                        CustomChoseTypeDialog(
                            textTitle = "Please choose where to save your password",
                            textSubTitle = "Be careful, because saving to the blockchain consumes the ton cryptocurrency",
                            textButtonFirst = "Ton storage",
                            textButtonSecond = "Local storage",
                            handlerFirstButton = (stateOpenDialogLoading as StateAddDialog.Show).firstCallBack,
                            handlerSecondButton = (stateOpenDialogLoading as StateAddDialog.Show).secondCallBack
                        )
                    }
                }

                if (stateOpenDialogLoading is StateAddDialog.ShowLoading) {
                    Dialog(onDismissRequest = { stateOpenDialogLoading is StateAddDialog.ShowLoading }) {
                        CustomLoadingDialog()
                    }
                }

                if (stateOpenDialogLoading is StateAddDialog.Error) {
                    Dialog(onDismissRequest = { stateOpenDialogLoading is StateAddDialog.Error }) {
                        CustomErrorDialog(
                            textTitle = "An error has occurred",
                            textSubTitle = "An error occurred during the execution of the request. try again later",
                            textButton = "close",
                            handlerButton = {
                                component.onEvent(ScreenAddMultiPasswordEvent.CloseAllAlert)
                            }
                        )
                    }
                }

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
                            ScreenAddMultiPasswordEvent.UpdateTextTitle(
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
                            ScreenAddMultiPasswordEvent.UpdateTextEmailORUserName(
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
                            ScreenAddMultiPasswordEvent.UpdateTextPassword(
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
                        component.onEvent(ScreenAddMultiPasswordEvent.UpdateTextUrl(it))
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
                            ScreenAddMultiPasswordEvent.UpdateTextDescriptions(
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
                    ScreenAddMultiPasswordEvent.ClickButtonAddNewState(
                        databaseDriverFactory = DriverFactory(context)
                    )
                )
            }
        }
    }
}