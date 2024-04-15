package com.pass.word.session.android.screen.initialGreeting

import androidx.compose.runtime.Composable
import com.pass.word.session.navigation.screen.initialGreeting.screenFirstInitial.FirstInititalScreenContent
import com.pass.word.session.navigation.screen.initialGreeting.screenFirstInitial.ScreenFirstInitialComponent

@Composable
fun FirstInitialScreen(component: ScreenFirstInitialComponent) {
    FirstInititalScreenContent(
        onClickHandlerBtnNxt = { component.navigationToNextScreen() }
    )
}