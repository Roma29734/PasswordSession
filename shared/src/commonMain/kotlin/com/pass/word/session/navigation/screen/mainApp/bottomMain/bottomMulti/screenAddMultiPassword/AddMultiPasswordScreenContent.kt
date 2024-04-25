package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.pass.word.session.ui.viewComponent.CustomChoseTypeDialog
import com.pass.word.session.ui.viewComponent.CustomErrorDialog
import com.pass.word.session.ui.viewComponent.CustomLoadingDialog
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.OutlineInputText

@Composable
fun AddMultiPasswordScreenContent(
    textTitle: String,
    textEmailORUserName: String,
    textPassword: String,
    textUrl: String,
    textDescriptions: String,
    stateOpenDialogLoading: StateAddDialog,
    driverFactory: DriverFactory,
    eventComponentDispatch: (ScreenAddMultiPasswordEvent) -> Unit,
) {
    val focusRequesterEmailUserName = remember { FocusRequester() }
    val focusRequesterTitle = remember { FocusRequester() }
    val focusRequesterPassword = remember { FocusRequester() }
    val focusRequesterUrl = remember { FocusRequester() }
    val focusRequesterDescriptions = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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
                            eventComponentDispatch(ScreenAddMultiPasswordEvent.CloseAllAlert)
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
                    eventComponentDispatch(
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
                    eventComponentDispatch(
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
                    eventComponentDispatch(
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
                    eventComponentDispatch(ScreenAddMultiPasswordEvent.UpdateTextUrl(it))
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
                        ScreenAddMultiPasswordEvent.UpdateTextDescriptions(
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
            text = "add new password",
            true
        ) {
            eventComponentDispatch(
                ScreenAddMultiPasswordEvent.ClickButtonAddNewState(
                    databaseDriverFactory = driverFactory
                )
            )
        }
    }
}