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
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.ScreenEnterSeedPhraseEvent
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.ScreenSeedPhraseSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.ScreenSeedPhraseSettingsEvent
import com.pass.word.session.ui.CustomColor
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
                        component.onEvent(ScreenSeedPhraseSettingsEvent.ContinueDialogEvent(DriverFactory(context)))
                    }
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


@Composable
fun ItemPhrase(text: String) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 64.dp, end = 64.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(16.dp),
        border = BorderStroke(2.dp, CustomColor().grayLight),
    ) {
        Row(
            modifier = Modifier
                .background(CustomColor().mainBlue)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
            )
        }
    }
}


@Composable
fun DialogLogOut(
    cancelHandler: () -> Unit,
    continueHandler: () -> Unit
) {


    var stateTimer by remember { mutableIntStateOf(15) }

    DisposableEffect(Unit) {
        val job = CoroutineScope(Dispatchers.Default).launch {
            repeat(15) {
                delay(1000)
                stateTimer -= 1
            }
        }
        onDispose {
            job.cancel() // Отмена корутины при удалении компонента
        }
    }


    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Image(
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                colorFilter = ColorFilter.tint(CustomColor().brandRedMain)
            )

            Column(modifier = Modifier.padding(16.dp)) {

                if (stateTimer != 0) {
                    Text(
                        text = stateTimer.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


                Text(
                    text = "Dangerous",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "when you click continue, you exit the application and all data will be erased forever, they will remain only in the blockchain",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {

                TextButton(
                    onClick = {
                        cancelHandler()
                    },
                    Modifier
                        .background(CustomColor().brandBlueLight)
                        .weight(1f)
                ) {
                    Text(
                        "Cancel",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .background(CustomColor().brandBlueLight)
                    )
                }

                TextButton(
                    onClick = {
                        continueHandler()
                    }, enabled = stateTimer == 0,
                    modifier = Modifier
                        .background(if (stateTimer == 0) CustomColor().brandRedMain else CustomColor().grayLight)
                        .weight(1f)
                ) {
                    Text(
                        "Continue",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }

        }
    }

}
