package com.pass.word.session.navigation.screen.bottom.screenSettingsComponent


interface ScreenSettingsStateEvent {
    data class ClickToButtonDownloadPass(val context: Any) : ScreenSettingsStateEvent
    data object OnNavigateToChangePasswordComponent: ScreenSettingsStateEvent
}