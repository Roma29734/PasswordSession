package com.pass.word.session.android.screen.seedPhraseSettings

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.initialGreeting.enterSeedPhrase.CustomDialogUI
import com.pass.word.session.android.screen.initialGreeting.enterSeedPhrase.InputSeedContainer
import com.pass.word.session.android.screen.viewComponent.CustomImageModel
import com.pass.word.session.android.screen.viewComponent.DialogLogOut
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.ScreenEnterSeedPhraseEvent
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.ScreenSeedPhraseSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.ScreenSeedPhraseSettingsEvent
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.ItemPhrase
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun SeedPhraseSettingsScreen(component: ScreenSeedPhraseSettingsComponent) {

    val seedPhraseState by component.seedPhraseState.collectAsState()
    val stateVisibleDialog by component.stateVisibleDialog.collectAsState()
    val context = LocalContext.current

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
                        component.onEvent(ScreenSeedPhraseSettingsEvent.HideDialogEvent)
                    },
                    continueHandler = {
                        component.onEvent(
                            ScreenSeedPhraseSettingsEvent.ContinueDialogEvent(
                                DriverFactory(context)
                            )
                        )
                    },
                    textCancelButton = "Cancel",
                    textContinueButton = "Continue",
                    customImageModel = CustomImageModel(
                        painter = painterResource(id = R.drawable.ic_warning),
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
            component.onEvent(ScreenSeedPhraseSettingsEvent.NavToBack)
        })


        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                content = {

                    item {
                        Text(
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
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
                            component.onEvent(ScreenSeedPhraseSettingsEvent.ClickButtonLogOut)
                        }
                    }
                }
            )

        }

    }
}



