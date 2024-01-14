package com.pass.word.session.navigation.screen.bottom.screenSettingsComponent

import com.pass.word.session.data.DriverFactory


interface ScreenSettingsStateEvent {
    data class ClickToButtonDownloadPass(val context: Any, val databaseDriverFactory: DriverFactory) : ScreenSettingsStateEvent
    data object OnNavigateToChangePasswordComponent: ScreenSettingsStateEvent
}