package com.pass.word.session.navigation.screen.main.changePassword

interface ChangePasswordEvent {
    data object ClickButtonBack: ChangePasswordEvent
    data class StateUpdatePassItem(val newCod: Int): ChangePasswordEvent
}