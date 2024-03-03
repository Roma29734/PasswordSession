package com.pass.word.session.android.screen.bottomScreen.settingsScreen.changePassword

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.viewComponent.MainComponentButton
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.main.changePassword.ScreenWarningComponent
import com.pass.word.session.ui.CustomColor

@Composable
fun WarningScreen(component: ScreenWarningComponent) {

    var visibleState: Boolean by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        visibleState = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(Modifier.fillMaxWidth()) {
            UpBarButtonBack(onBackHandler = {
                component.onBackNavigate()
            })
        }

        AnimatedVisibility(
            visible = visibleState,
            enter = scaleIn(animationSpec = tween(durationMillis = 600, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = "ic warning",
                modifier = Modifier
                    .size(190.dp)
                    .padding(top = 64.dp),
                colorFilter = ColorFilter.tint(CustomColor().brandRedMain)
            )

        }

        AnimatedVisibility(
            visible = visibleState,
            enter = fadeIn(animationSpec = tween(durationMillis = 800, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300)),
        ) {

            Text(
                text = "warning, if you forget your password, you will not be able to reset it, that is, you will lose all your data, treat the next step with care, by clicking continue you agree that all your data may be lost beyond recovery",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        MainComponentButton(text = "continue", true) { component.onNextNavigate() }

    }
}