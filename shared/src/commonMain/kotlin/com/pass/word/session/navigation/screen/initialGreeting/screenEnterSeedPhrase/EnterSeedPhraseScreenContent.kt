package com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pass.word.session.ui.viewComponent.CustomDialogUI
import com.pass.word.session.ui.viewComponent.InputSeedContainer
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.UpBarButtonBack

@Composable
fun EnterSeedPhraseScreenContent(
    passEnterState: Boolean,
    stateSeedText: List<String>,
    stateOpenDialogLoading: Boolean,
    stateLoadingAlert: StateLoadSeedPhrase,
    eventDispatch: (ScreenEnterSeedPhraseEvent) -> Unit,
) {

    val focusRequesters = remember {
        List(24) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (stateOpenDialogLoading) {
            Dialog(onDismissRequest = { !stateOpenDialogLoading }) {
                CustomDialogUI(
                    openDialogCustom = stateOpenDialogLoading,
                    stateLoadSeedPhrase = stateLoadingAlert,
                    continueButtonHandler = {
                        eventDispatch(ScreenEnterSeedPhraseEvent.NavToNextScreen)
                    }
                )
            }
        }
        UpBarButtonBack(onBackHandler = {
            eventDispatch(ScreenEnterSeedPhraseEvent.NavToBack)
        })

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                content = {

                    item {
                        Text(
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            text = "enter your SED phrase of your wallet tone",
                            style = MaterialTheme.typography.displayLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }

                    itemsIndexed(
                        items = List(24) { index -> "Текстовое поле $index" },
                        key = { index, _ -> index }
                    ) { index, text ->
                        val textState = remember { mutableStateOf(stateSeedText[index]) }
                        InputSeedContainer(
                            textItem = textState.value,
                            focusRequesterTitle = focusRequesters[index],
                            onNextHandler = {
                                if (index != 23) {
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    focusManager.clearFocus()
                                }
                            },
                            changeTextItemHandler = {
                                textState.value = it
                                eventDispatch(
                                    ScreenEnterSeedPhraseEvent.ChangeHandlerInputItem(
                                        it,
                                        index
                                    )
                                )
                            }
                        )
                    }
                }
            )
        }
        MainComponentButton("continue", passEnterState) {
            eventDispatch(ScreenEnterSeedPhraseEvent.SendSeed)
        }
    }

}