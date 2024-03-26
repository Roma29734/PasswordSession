package com.pass.word.session.android.screen.initialGreeting.enterPassKeySecret

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.android.screen.initialGreeting.enterSeedPhrase.InputSeedContainer
import com.pass.word.session.android.screen.viewComponent.CustomErrorDialog
import com.pass.word.session.android.screen.viewComponent.CustomLoadingDialog
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret.ScreenEnterPassKeySecretComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret.ScreenEnterPassKeySecretEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.utilits.StateBasicLoadingDialog

@Composable
fun EnterPassKeySecretScreen(component: ScreenEnterPassKeySecretComponent) {

    val statePassKeySecret by component.statePassKeySecret.collectAsState()
    val stateEnableButtonNext by component.stateEnableButtonNext.collectAsState()
    val stateLoading by component.stateLoading.collectAsState()
    val warningText by component.warningText.collectAsState()

    val focusRequesters = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current


    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        UpBarButtonBack(onBackHandler = {
            component.onEvent(ScreenEnterPassKeySecretEvent.NavToBack)
        })

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Please enter your key for pass", style = MaterialTheme.typography.displayLarge,
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
                    component.onEvent(ScreenEnterPassKeySecretEvent.UpdateStateItemText(it))
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
            component.onEvent(ScreenEnterPassKeySecretEvent.ClickContinueButton)
        }

        if (stateLoading is StateBasicLoadingDialog.ShowLoading) {
            Dialog(onDismissRequest = { stateLoading as StateBasicLoadingDialog.ShowLoading }) {
                CustomLoadingDialog()
            }
        }

        if (stateLoading is StateBasicLoadingDialog.Error) {
            Dialog(onDismissRequest = { stateLoading is StateBasicLoadingDialog.Error }) {
                CustomErrorDialog(
                    textTitle = "An error has occurred",
                    textSubTitle = (stateLoading as StateBasicLoadingDialog.Error).message,
                    textButton = "close",
                    handlerButton = {
                        component.onEvent(ScreenEnterPassKeySecretEvent.HideLoadingDialog)
                    }
                )
            }
        }
    }
}