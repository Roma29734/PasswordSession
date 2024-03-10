package com.pass.word.session.navigation.screen.mainApp.changePassword

interface ChangePasswordEvent {
    data object ClickButtonBack: ChangePasswordEvent
    data class StateUpdatePassItem(val newCod: Int): ChangePasswordEvent
}