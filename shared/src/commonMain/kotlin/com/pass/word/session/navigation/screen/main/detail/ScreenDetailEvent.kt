package com.pass.word.session.navigation.screen.main.detail

interface ScreenDetailEvent {
    data object ClickButtonBack: ScreenDetailEvent
    data class ShowToast(val message: String): ScreenDetailEvent
}