package com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType

interface ScreenChoosingTypeEvent {
    data object NavToLocalVersion : ScreenChoosingTypeEvent
    data class ChangeAcceptedPrivacyState(private val state: Boolean) : ScreenChoosingTypeEvent
    data object NavToTonVersion : ScreenChoosingTypeEvent
    data object NavToBack : ScreenChoosingTypeEvent
}