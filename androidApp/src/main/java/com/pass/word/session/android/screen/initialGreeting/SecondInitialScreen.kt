package com.pass.word.session.android.screen.initialGreeting

import androidx.compose.runtime.Composable
import com.pass.word.session.navigation.screen.initialGreeting.screenSecondInitial.ScreenSecondInitialComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenSecondInitial.SecondInitialScreenContent

@Composable
fun SecondInitialScreen(component: ScreenSecondInitialComponent) {
    SecondInitialScreenContent {component.navigateToMainScreen()}
}