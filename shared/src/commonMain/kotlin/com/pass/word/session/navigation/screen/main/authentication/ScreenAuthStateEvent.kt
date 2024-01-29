package com.pass.word.session.navigation.screen.main.authentication

interface ScreenAuthStateEvent {
    data class ReactionToFollowingBiometrics(val successState: Boolean, val errorMessage: String?, val context: Any) :
        ScreenAuthStateEvent
    data class ReactionToFirstBiometrics(val successState: Boolean, val errorMessage: String?, val context: Any): ScreenAuthStateEvent
    data class StateUpdatePassItem(val number: String, val context: Any) : ScreenAuthStateEvent
}