package screen.mainApp.passKeySettings.editPassKey

import Img.MyIconPack
import Img.myiconpack.IcWarning
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.ScreenEditPassKeyComponent
import com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.ScreenPassKeySettingsEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.CustomErrorDialog
import com.pass.word.session.ui.viewComponent.CustomImageModel
import com.pass.word.session.ui.viewComponent.CustomLoadingDialog
import com.pass.word.session.ui.viewComponent.DialogLogOut
import com.pass.word.session.ui.viewComponent.InputSeedContainer
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import com.pass.word.session.utilits.StateTwosItemDialog

@Composable
fun EditPassKeyScreen(component: ScreenEditPassKeyComponent) {

    val statePassKeySecret by component.statePassKeySecret.collectAsState()
    val stateEnableButtonNext by component.stateEnableButtonNext.collectAsState()
    val focusRequesters = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val warningText by component.warningText.collectAsState()
    val stateShowedDialog by component.stateShowedDialog.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (stateShowedDialog is StateTwosItemDialog.ShowOneDialog) {
            Dialog(onDismissRequest = { stateShowedDialog is StateTwosItemDialog.ShowOneDialog }) {
                DialogLogOut(
                    cancelHandler = { component.onEvent(ScreenPassKeySettingsEvent.HideDialog) },
                    continueHandler = { component.onEvent(ScreenPassKeySettingsEvent.OnNext) },
                    textCancelButton = "Cancel",
                    textContinueButton = "Continue",
                    customImageModel = CustomImageModel(
                        painter = MyIconPack.IcWarning,
                        color = CustomColor().brandRedMain,
                        contentScale = ContentScale.Fit
                    ),
                    notifiText = "Dangerous",
                    subTitleText = "Attention, the procedure for changing the secret phrase is currently underway, do not log out of the application and do not turn off the Internet to avoid errors or data loss",
                    startTime = 15
                )
            }
        }

        if (stateShowedDialog is StateTwosItemDialog.ShowTwoDialog) {
            Dialog(onDismissRequest = { stateShowedDialog is StateTwosItemDialog.ShowTwoDialog }) {
                CustomLoadingDialog()
            }
        }

        if(stateShowedDialog is StateTwosItemDialog.Error) {
            Dialog(onDismissRequest = { stateShowedDialog is StateTwosItemDialog.Error }) {
                CustomErrorDialog(
                    textTitle = "An error has occurred",
                    textSubTitle = "An error occurred during the execution of the request. try again later",
                    textButton = "close",
                    handlerButton = {
                        component.onEvent(ScreenPassKeySettingsEvent.HideDialog)
                        component.onEvent(ScreenPassKeySettingsEvent.OnBack)
                    }
                )
            }
        }

        UpBarButtonBack(onBackHandler = {
            component.onEvent(ScreenPassKeySettingsEvent.OnBack)
        })

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                text = "Attention, this is your secret phrase that encrypts your passwords, do not give it to anyone",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(64.dp))

            InputSeedContainer(
                textItem = statePassKeySecret,
                focusRequesterTitle = focusRequesters,
                onNextHandler = {
                    focusManager.clearFocus()
                },
                changeTextItemHandler = {
                    component.onEvent(ScreenPassKeySettingsEvent.ChangeItemText(it))
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

        MainComponentButton(
            "save change",
            stateEnableButtonNext,
            colorButton = CustomColor().brandRedMain
        ) {
            component.onEvent(ScreenPassKeySettingsEvent.OnClickButtonChange)
        }
    }
}