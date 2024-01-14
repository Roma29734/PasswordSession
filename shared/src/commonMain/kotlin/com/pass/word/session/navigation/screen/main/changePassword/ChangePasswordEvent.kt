package com.pass.word.session.navigation.screen.main.changePassword

import com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterInitialPassAuth.ScreenEnterInitialPassAuthEvent

interface ChangePasswordEvent {
    data object ClickButtonBack: ChangePasswordEvent
    data class StateUpdatePassItem(val newCod: Int): ChangePasswordEvent
}