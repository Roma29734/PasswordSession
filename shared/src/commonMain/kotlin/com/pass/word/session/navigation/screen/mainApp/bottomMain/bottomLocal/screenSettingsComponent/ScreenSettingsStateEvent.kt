package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent

import com.pass.word.session.data.DriverFactory


interface ScreenSettingsStateEvent {
    data class ClickToButtonDownloadPass(val context: Any, val databaseDriverFactory: DriverFactory) :
        ScreenSettingsStateEvent
    data object OnNavigateToChangePasswordComponent: ScreenSettingsStateEvent
    data object OnNavigateToImportPassword : ScreenSettingsStateEvent
    data object OnNavigateToSeedPhraseSettings: ScreenSettingsStateEvent
    data object OnNavigateToPassKeySettingsComponent: ScreenSettingsStateEvent
}

enum class ItemSettings {
    ImportPassword, ChangePassword, SeedPhraseSettings, PassKeySettings,Telegram, GitHub
}