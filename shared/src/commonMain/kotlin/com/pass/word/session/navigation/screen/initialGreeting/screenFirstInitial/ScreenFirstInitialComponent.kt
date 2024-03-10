package com.pass.word.session.navigation.screen.initialGreeting.screenFirstInitial

import com.arkivanov.decompose.ComponentContext

class ScreenFirstInitialComponent constructor(
    componentContext: ComponentContext,
    val navigateToNextScreen: () -> Unit
) : ComponentContext by componentContext {

    fun navigationToNextScreen() {
        navigateToNextScreen()
    }
}