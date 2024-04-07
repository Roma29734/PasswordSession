package com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings

import com.pass.word.session.data.DriverFactory

interface ScreenSeedPhraseSettingsEvent {
    data object NavToBack: ScreenSeedPhraseSettingsEvent
    data object ClickButtonLogOut: ScreenSeedPhraseSettingsEvent
    data object HideDialogEvent: ScreenSeedPhraseSettingsEvent
    data class ContinueDialogEvent(val databaseDriverFactory: DriverFactory): ScreenSeedPhraseSettingsEvent
}