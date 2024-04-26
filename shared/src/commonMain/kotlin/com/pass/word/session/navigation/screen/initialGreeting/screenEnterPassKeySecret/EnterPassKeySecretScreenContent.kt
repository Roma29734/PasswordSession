package com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.CustomErrorDialog
import com.pass.word.session.ui.viewComponent.CustomLoadingDialog
import com.pass.word.session.ui.viewComponent.InputSeedContainer
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import com.pass.word.session.utilits.StateBasicDialog

@Composable
fun EnterPassKeySecretScreenContent(
    statePassKeySecret: String,
    stateEnableButtonNext: Boolean,
    stateLoading: StateBasicDialog,
    warningText: String?,
    eventComponentDispatch: (ScreenEnterPassKeySecretEvent) -> Unit,
) {

    val focusRequesters = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current


    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background).verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        UpBarButtonBack(onBackHandler = {
            eventComponentDispatch(ScreenEnterPassKeySecretEvent.NavToBack)
        })

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                text = "Please enter your key for pass", style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.size(24.dp))

            InputSeedContainer(
                textItem = statePassKeySecret,
                focusRequesterTitle = focusRequesters,
                onNextHandler = {
                    focusManager.clearFocus()
                },
                changeTextItemHandler = {
                    eventComponentDispatch(ScreenEnterPassKeySecretEvent.UpdateStateItemText(it))
                }
            )

            Spacer(modifier = Modifier.size(24.dp))

            if (warningText != null) {
                Text(
                    warningText!!,
                    style = MaterialTheme.typography.displayMedium,
                    color = CustomColor().brandRedMain,
                    textAlign = TextAlign.Center,
                )
            }

        }

        MainComponentButton("continue", stateEnableButtonNext) {
            eventComponentDispatch(ScreenEnterPassKeySecretEvent.ClickContinueButton)
        }

        if (stateLoading is StateBasicDialog.Show) {
            Dialog(onDismissRequest = { stateLoading as StateBasicDialog.Show }) {
                CustomLoadingDialog()
            }
        }

        if (stateLoading is StateBasicDialog.Error) {
            Dialog(onDismissRequest = { stateLoading is StateBasicDialog.Error }) {
                CustomErrorDialog(
                    textTitle = "An error has occurred",
                    textSubTitle = (stateLoading as StateBasicDialog.Error).message,
                    textButton = "close",
                    handlerButton = {
                        eventComponentDispatch(ScreenEnterPassKeySecretEvent.HideLoadingDialog)
                    }
                )
            }
        }
    }

}