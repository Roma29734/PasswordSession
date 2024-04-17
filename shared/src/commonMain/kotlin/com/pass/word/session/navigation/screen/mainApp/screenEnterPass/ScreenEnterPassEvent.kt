package com.pass.word.session.navigation.screen.mainApp.screenEnterPass

interface ScreenEnterPassEvent {
    data object ClickButtonBack: ScreenEnterPassEvent
    data class StateUpdatePassItem(val newCod: Int, val context: Any?):
        ScreenEnterPassEvent
}