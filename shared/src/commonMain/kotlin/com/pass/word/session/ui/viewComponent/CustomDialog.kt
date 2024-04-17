package com.pass.word.session.ui.viewComponent

import Img.MyIconPack
import Img.myiconpack.IcComplete
import Img.myiconpack.IcError
import Img.myiconpack.IcSave
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.StateLoadSeedPhrase
import com.pass.word.session.ui.CustomColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomLoadingDialog() {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    text = "Your data is being downloaded from the blockchain",
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


@Composable
fun CustomChoseTypeDialog(
    textTitle: String,
    textSubTitle: String,
    textButtonFirst: String,
    textButtonSecond: String,
    handlerFirstButton: () -> Unit,
    handlerSecondButton: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                imageVector = MyIconPack.IcSave,
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                colorFilter = ColorFilter.tint(CustomColor().brandGreen)
            )


            Text(
                text = textTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                text = textSubTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColor().grayLight
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .background(CustomColor().brandBlueLight),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                TextButton(onClick = {
                    handlerFirstButton()
                }) {
                    Text(
                        textButtonFirst,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(CustomColor().grayLight),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    handlerSecondButton()
                }) {
                    Text(
                        textButtonSecond,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CustomErrorDialog(
    textTitle: String,
    textSubTitle: String,
    textButton: String,
    handlerButton: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                imageVector = MyIconPack.IcError,
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                colorFilter = ColorFilter.tint(CustomColor().brandRedMain)
            )


            Text(
                text = textTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                text = textSubTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColor().grayLight
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .background(CustomColor().brandBlueLight),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                TextButton(onClick = {
                    handlerButton()
                }) {
                    Text(
                        textButton,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
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
                    imageVector = MyIconPack.IcComplete,
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

data class CustomImageModel(
    val painter: ImageVector,
    val color: Color,
    val contentScale: ContentScale
)

@Composable
fun DialogLogOut(
    cancelHandler: () -> Unit,
    continueHandler: () -> Unit,
    textCancelButton: String,
    textContinueButton: String,
    customImageModel: CustomImageModel,
    notifiText: String,
    subTitleText: String,
    startTime: Int
) {

    var timeStartItem = startTime

    if(startTime > 5) {
        timeStartItem = 15
    }

    var stateTimer by remember { mutableIntStateOf(timeStartItem) }

    DisposableEffect(Unit) {
        val job = CoroutineScope(Dispatchers.Default).launch {
            repeat(timeStartItem) {
                delay(1000)
                stateTimer -= 1
            }
        }
        onDispose {
            job.cancel()
        }
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            Modifier
                .background(CustomColor().mainBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Image(
                imageVector = customImageModel.painter,
                contentDescription = null, // decorative
                contentScale = customImageModel.contentScale,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                colorFilter = ColorFilter.tint(customImageModel.color)
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
                    text = notifiText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = subTitleText,
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
                        textCancelButton,
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
                        textContinueButton,
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