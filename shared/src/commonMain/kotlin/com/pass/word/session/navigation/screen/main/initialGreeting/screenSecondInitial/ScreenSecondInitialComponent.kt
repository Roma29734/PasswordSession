package com.pass.word.session.navigation.screen.main.initialGreeting.screenSecondInitial

import com.arkivanov.decompose.ComponentContext

class ScreenSecondInitialComponent constructor(
    componentContext: ComponentContext,
    private val onNavigateToNextScreen: () -> Unit
) : ComponentContext by componentContext {

    fun navigateToMainScreen() {
        onNavigateToNextScreen()
    }
}