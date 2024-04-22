package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.OutlineInputText
import kotlinx.coroutines.launch

@Composable
fun AddPasswordScreenContent(
    textTitle: String,
    textEmailORUserName: String,
    textPassword: String,
    textUrl: String,
    textDescriptions: String,
    eventComponentDispatch: (ScreenAddPasswordStateEvent) -> Unit,
    driverFactory: DriverFactory
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
                    eventComponentDispatch(
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
                    eventComponentDispatch(
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
                    eventComponentDispatch(ScreenAddPasswordStateEvent.UpdateTextUrl(it))
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
                        ScreenAddPasswordStateEvent.UpdateTextDescriptions(
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
                ScreenAddPasswordStateEvent.ClickButtonAddNewState(
                    databaseDriverFactory = driverFactory
                )
            )
        }
    }


}