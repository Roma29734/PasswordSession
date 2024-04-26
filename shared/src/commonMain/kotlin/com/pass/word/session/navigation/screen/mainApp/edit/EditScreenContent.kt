package com.pass.word.session.navigation.screen.mainApp.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.ui.viewComponent.CustomErrorDialog
import com.pass.word.session.ui.viewComponent.CustomLoadingDialog
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.OutlineInputText
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import com.pass.word.session.utilits.StateBasicDialog

@Composable
fun EditScreenContent(
    textTitle: String,
    textEmailORUserName: String,
    textPassword: String,
    textUrl: String,
    textDescriptions: String,
    stateOpenDialogLoading: StateBasicDialog,
    driverFactory: DriverFactory,
    eventComponentDispatch: (ScreenEditEvent) -> Unit,
) {

    val focusRequesterEmailUserName = remember { FocusRequester() }
    val focusRequesterTitle = remember { FocusRequester() }
    val focusRequesterPassword = remember { FocusRequester() }
    val focusRequesterUrl = remember { FocusRequester() }
    val focusRequesterDescriptions = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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
                    eventComponentDispatch(ScreenEditEvent.CloseAllAlert)
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background).verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            UpBarButtonBack(onBackHandler = {
                eventComponentDispatch(ScreenEditEvent.ClickButtonBack)
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
                onValueChangeHandler = { eventComponentDispatch(ScreenEditEvent.UpdateTextTitle(it)) },
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
                    eventComponentDispatch(
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
                    eventComponentDispatch(
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
                    eventComponentDispatch(ScreenEditEvent.UpdateTextUrl(it))
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
                    eventComponentDispatch(
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
            eventComponentDispatch(ScreenEditEvent.ClickButtonUpdate(driverFactory))
        }

    }
}