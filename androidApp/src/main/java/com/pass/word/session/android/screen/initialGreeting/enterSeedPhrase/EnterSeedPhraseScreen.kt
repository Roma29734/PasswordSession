package com.pass.word.session.android.screen.initialGreeting.enterSeedPhrase

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.ScreenEnterSeedPhraseComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.ScreenEnterSeedPhraseEvent
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.StateLoadSeedPhrase
import com.pass.word.session.ui.CustomColor

@Composable
fun EnterSeedPhraseScreen(component: ScreenEnterSeedPhraseComponent) {

    val passEnterState by component.stateEnableButtonNext.collectAsState()
    val stateSeedText by component.stateSeedText.collectAsState().value
    val stateOpenDialogLoading by component.stateOpenDialogLoading.collectAsState()
    val stateLoadingAlert by component.stateLoadingAlert.collectAsState()

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
                        component.onEvent(ScreenEnterSeedPhraseEvent.NavToNextScreen)
                    }
                )
            }
        }
        UpBarButtonBack(onBackHandler = {
            component.onEvent(ScreenEnterSeedPhraseEvent.NavToBack)
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
                                component.onEvent(
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
            component.onEvent(ScreenEnterSeedPhraseEvent.SendSeed)
        }
    }
}

@Composable
fun InputSeedContainer(
    textItem: String,
    focusRequesterTitle: FocusRequester,
    onNextHandler: () -> Unit,
    changeTextItemHandler: (itemText: String) -> Unit
) {
    OutlinedTextField(
        value = textItem,
        textStyle = MaterialTheme.typography.displaySmall.plus(TextStyle(color = Color.White)),
        onValueChange = { changeTextItemHandler(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
            .focusRequester(focusRequesterTitle),
        keyboardActions = KeyboardActions(onNext = {
            onNextHandler()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.White,
            focusedBorderColor = Color.White, // цвет при получении фокуса
            unfocusedBorderColor = CustomColor().grayLight,  // цвет при отсутствии фокуса
        ),
    )
}

//Layout
@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    openDialogCustom: Boolean,
    stateLoadSeedPhrase: StateLoadSeedPhrase,
    continueButtonHandler: () -> Unit
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //.......................................................................

            if (stateLoadSeedPhrase is StateLoadSeedPhrase.InSuccess) {
                Image(
                    painter = painterResource(id = R.drawable.ic_complete),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),

                    colorFilter = ColorFilter.tint(CustomColor().brandGreen)
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Is Successful",
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
                        .padding(top = 10.dp)
                        .background(CustomColor().brandBlueLight),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    androidx.compose.material3.TextButton(onClick = {
                        continueButtonHandler()
                    }) {
                        Text(
                            "Continue",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .height(70.dp),
                    color = CustomColor().brandBlueLight
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Please wait",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Your wallet is being checked, it may take some time.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}