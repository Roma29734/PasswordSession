package com.pass.word.session.navigation.screen.main.initialGreeting.screenChoosingType

import com.arkivanov.decompose.ComponentContext

class ScreenChoosingTypeComponent(
    componentContext: ComponentContext,
    private val navToLocalType: () -> Unit,
    private val navToTonType: () -> Unit,
    private val navToBack: () -> Unit,
) : ComponentContext by componentContext {

    fun onEvent(event: ScreenChoosingTypeEvent) {
        when(event) {
            is ScreenChoosingTypeEvent.ChangeAcceptedPrivacyState -> {

            }
            is ScreenChoosingTypeEvent.NavToLocalVersion -> {
                navToLocalType()
            }
            is ScreenChoosingTypeEvent.NavToTonVersion -> {
                navToTonType()
            }
            is ScreenChoosingTypeEvent.NavToBack -> {
                navToBack()
            }
        }
    }
}