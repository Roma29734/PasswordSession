package com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings

import Img.MyIconPack
import Img.myiconpack.IcWarning
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.CustomImageModel
import com.pass.word.session.ui.viewComponent.DialogLogOut
import com.pass.word.session.ui.viewComponent.ItemPhrase
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import com.pass.word.session.utilits.StateBasicDialog

@Composable
fun SeedPhraseSettingsScreenContent(
    seedPhraseState: List<String>?,
    stateVisibleDialog: StateBasicDialog,
    driverFactory: DriverFactory,
    eventComponentDispatch: (ScreenSeedPhraseSettingsEvent) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (stateVisibleDialog is StateBasicDialog.Show) {
            Dialog(onDismissRequest = { stateVisibleDialog is StateBasicDialog.Hide }) {
                DialogLogOut(
                    cancelHandler = {
                        eventComponentDispatch(ScreenSeedPhraseSettingsEvent.HideDialogEvent)
                    },
                    continueHandler = {
                        eventComponentDispatch(
                            ScreenSeedPhraseSettingsEvent.ContinueDialogEvent(
                                driverFactory
                            )
                        )
                    },
                    textCancelButton = "Cancel",
                    textContinueButton = "Continue",
                    customImageModel = CustomImageModel(
                        painter = MyIconPack.IcWarning,
                        color = CustomColor().brandRedMain,
                        contentScale = ContentScale.Fit
                    ),
                    notifiText = "Dangerous",
                    subTitleText = "when you click continue, you exit the application and all data will be erased forever, they will remain only in the blockchain",
                    startTime = 15
                )
            }
        }

        UpBarButtonBack(onBackHandler = {
            eventComponentDispatch(ScreenSeedPhraseSettingsEvent.NavToBack)
        })


        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                content = {

                    item {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth(),
                            text = "your SED phrase of your wallet tone",
                            style = MaterialTheme.typography.displayLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }

                    if (seedPhraseState != null) {
                        itemsIndexed(
                            items = seedPhraseState!!,
                            key = { index, _ -> index }
                        ) { index, text ->
                            ItemPhrase(text)
                        }
                    }
                    item {

                        Spacer(modifier = Modifier.size(16.dp))

                        MainComponentButton(
                            "Log out",
                            true,
                            colorButton = CustomColor().brandRedMain
                        ) {
                            eventComponentDispatch(ScreenSeedPhraseSettingsEvent.ClickButtonLogOut)
                        }
                    }
                }
            )
        }
    }
}