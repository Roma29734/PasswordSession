package com.pass.word.session.navigation.screen.main.detail

interface ScreenDetailEvent {
    data object ClickButtonBack: ScreenDetailEvent
    data class ShowToast(val message: String): ScreenDetailEvent
    data class ChangeStateOpenedAlertDialog(val newState: Boolean) : ScreenDetailEvent
    data object DeleteItemPass: ScreenDetailEvent
}