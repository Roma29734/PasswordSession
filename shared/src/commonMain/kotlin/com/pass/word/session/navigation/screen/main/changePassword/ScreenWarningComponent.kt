package com.pass.word.session.navigation.screen.main.changePassword

import com.arkivanov.decompose.ComponentContext

class ScreenWarningComponent constructor(
    componentContext: ComponentContext,
    private val onBackNavigation: () -> Unit,
    private val onNextScreenNavigation: () -> Unit,
): ComponentContext by componentContext {

    fun onBackNavigate() {
        onBackNavigation()
    }

    fun onNextNavigate() {
        onNextScreenNavigation()
    }
}