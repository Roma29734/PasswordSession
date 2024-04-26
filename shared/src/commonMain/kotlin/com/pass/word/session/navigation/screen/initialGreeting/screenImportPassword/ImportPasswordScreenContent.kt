package com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword

import Img.MyIconPack
import Img.myiconpack.IcComplete
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.ui.CustomColor
import com.pass.word.session.ui.viewComponent.MainComponentButton
import com.pass.word.session.ui.viewComponent.UpBarButtonBack
import kotlin.math.max

@Composable
fun ImportPasswordScreenContent(
    stateShowCompleteView: Boolean,
    stateBack: Boolean,
    visibleState: Boolean,
    visibleCompleteImportState: Boolean,
    checkSelectedFile: () -> Unit,
    eventComponentDispatch: (ScreenImportPasswordEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background).verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        if (stateBack) {
            UpBarButtonBack(onBackHandler = {
                eventComponentDispatch(ScreenImportPasswordEvent.ClickBackButton)
            })
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (stateShowCompleteView) {
                AnimatedVisibility(
                    visible = visibleCompleteImportState,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                ) {
                    Image(
                        modifier = Modifier.size(128.dp),
                        imageVector = MyIconPack.IcComplete,
                        contentDescription = "ic complete",
                        colorFilter = ColorFilter.tint(CustomColor().brandGreen)
                    )
                }
                AnimatedVisibility(
                    visible = visibleCompleteImportState,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Import complete!",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(32.dp))

                AnimatedVisibility(
                    visible = visibleState,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "You can import your passwords, for this you need to provide permission",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.size(32.dp))
                AnimatedVisibility(
                    visible = visibleState,
                    enter = scaleIn(
                        animationSpec = tween(
                            durationMillis = 150,
                            easing = LinearEasing
                        )
                    ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                ) {
                    Button(
                        onClick = {
                            checkSelectedFile()
                        },
                        modifier = Modifier
                            .widthIn(max = 350.dp).fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(CustomColor().brandBlueLight)
                    ) {
                        Text(
                            text = "Import",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
        MainComponentButton(
            "Next", true
        ) { eventComponentDispatch(ScreenImportPasswordEvent.ClickButtonNext) }
    }
}