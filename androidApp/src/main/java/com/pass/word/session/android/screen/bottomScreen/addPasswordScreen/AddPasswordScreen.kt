package com.pass.word.session.android.screen.bottomScreen.addPasswordScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent.ScreenAddPasswordComponent
import com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent.ScreenAddPasswordStateEvent
import com.pass.word.session.ui.CustomColor
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
                .background(Color.Black)
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp),
                text = "Add New Password",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Text(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                text = "Title",
                style = MaterialTheme.typography.displaySmall,
                color = CustomColor().grayLight
            )
            OutlinedTextField(
                value = textTitle,
                onValueChange = { component.onEvent(ScreenAddPasswordStateEvent.UpdateTextTitle(it)) },
                textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .focusRequester(focusRequesterEmailUserName),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White, // цвет при получении фокуса
                    unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterTitle.requestFocus() }
                )

            )

            Text(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
                text = "Email/UserName",
                style = MaterialTheme.typography.displaySmall,
                color = CustomColor().grayLight
            )
            OutlinedTextField(
                value = textEmailORUserName,
                onValueChange = {
                    component.onEvent(
                        ScreenAddPasswordStateEvent.UpdateTextEmailORUserName(
                            it
                        )
                    )
                },
                textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .focusRequester(focusRequesterTitle),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White, // цвет при получении фокуса
                    unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterPassword.requestFocus() }
                )
            )
            Text(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
                text = "Password",
                style = MaterialTheme.typography.displaySmall,
                color = CustomColor().grayLight
            )
            OutlinedTextField(
                value = textPassword,
                onValueChange = {
                    component.onEvent(
                        ScreenAddPasswordStateEvent.UpdateTextPassword(
                            it
                        )
                    )
                },
                textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .focusRequester(focusRequesterPassword),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White, // цвет при получении фокуса
                    unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterUrl.requestFocus() }
                )
            )
            Text(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
                text = "Url",
                style = MaterialTheme.typography.displaySmall,
                color = CustomColor().grayLight
            )
            OutlinedTextField(
                value = textUrl,
                onValueChange = { component.onEvent(ScreenAddPasswordStateEvent.UpdateTextUrl(it)) },
                textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .focusRequester(focusRequesterUrl),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White, // цвет при получении фокуса
                    unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterDescriptions.requestFocus() }
                )
            )
            Text(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
                text = "Descriptions",
                style = MaterialTheme.typography.displaySmall,
                color = CustomColor().grayLight
            )
            OutlinedTextField(
                value = textDescriptions,
                onValueChange = {
                    component.onEvent(
                        ScreenAddPasswordStateEvent.UpdateTextDescriptions(
                            it
                        )
                    )
                },
                textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .focusRequester(focusRequesterDescriptions),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White, // цвет при получении фокуса
                    unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterDescriptions.freeFocus() }
                )
            )

            Spacer(modifier = Modifier.size(64.dp))

            Button(
                onClick = {
                    component.onEvent(
                        ScreenAddPasswordStateEvent.ClickButtonAddNewState(
                            databaseDriverFactory = DriverFactory(context)
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(CustomColor().brandBlueLight)
            ) {
                Text(
                    text = "Add new password",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
            }
        }
    }
}