package com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings

interface ScreenPassKeySettingsEvent {
    data class ChangeItemText(val newItemText: String) : ScreenPassKeySettingsEvent
    data object OnBack : ScreenPassKeySettingsEvent
    data object OnNext: ScreenPassKeySettingsEvent
    data object OnClickButtonChange: ScreenPassKeySettingsEvent
    data object HideDialog: ScreenPassKeySettingsEvent
}