package com.pass.word.session.android.screen.initialGreeting.choosingType

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.main.initialGreeting.screenChoosingType.ScreenChoosingTypeComponent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenChoosingType.ScreenChoosingTypeEvent
import com.pass.word.session.ui.CustomColor

@Composable
fun ChoosingTypeScreen(component: ScreenChoosingTypeComponent) {

    var visibleState: Boolean by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        visibleState = true
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UpBarButtonBack(onBackHandler = {
            component.onEvent(ScreenChoosingTypeEvent.NavToBack)
        })

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(
                visible = visibleState,
                enter = scaleIn(animationSpec = tween(durationMillis = 600, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)),
            ) {
                Image(
                    modifier = Modifier.size(128.dp),
                    painter = painterResource(id = R.drawable.ic_ton_logo),
                    contentDescription = "ic_ton_logo",
                )
            }

            Spacer(modifier = Modifier.size(64.dp))

            AnimatedVisibility(
                visible = visibleState,
                enter = fadeIn(animationSpec = tween(durationMillis = 800, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "SHA256 encryption is used to store your passwords on the ton network, in order to continue you must agree to the terms of",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .clickable {
                                // display term of use
                            },
                        text = "use",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline
                    )
                    Spacer(modifier = Modifier.size(64.dp))

                    Button(
                        onClick = {
                            component.onEvent(ScreenChoosingTypeEvent.NavToTonVersion)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(CustomColor().brandBlueLight)
                    ) {
                        Text(
                            text = "continue",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
        MainComponentButton("Use the local version", true) {
            component.onEvent(ScreenChoosingTypeEvent.NavToLocalVersion)
        }
    }
}