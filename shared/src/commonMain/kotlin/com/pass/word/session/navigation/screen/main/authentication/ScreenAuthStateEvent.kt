package com.pass.word.session.navigation.screen.main.authentication

interface ScreenAuthStateEvent {
    data class StateBiometric(val successState: Boolean, val errorMessage: String?): ScreenAuthStateEvent
    data class StateUpdatePassItem(val number: String): ScreenAuthStateEvent
}