package com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterInitialPassAuth

import com.pass.word.session.navigation.screen.main.authentication.ScreenAuthStateEvent

interface ScreenEnterInitialPassAuthEvent {
    data object ClickButtonBack: ScreenEnterInitialPassAuthEvent
    data class StateUpdatePassItem(val newCod: Int): ScreenEnterInitialPassAuthEvent
}