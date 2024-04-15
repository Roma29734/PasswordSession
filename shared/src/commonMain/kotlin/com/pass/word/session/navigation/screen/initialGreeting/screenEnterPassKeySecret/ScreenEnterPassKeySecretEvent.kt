package com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret

interface ScreenEnterPassKeySecretEvent {
    data class UpdateStateItemText(val newText: String) : ScreenEnterPassKeySecretEvent
    data object NavToBack : ScreenEnterPassKeySecretEvent
    data object ClickContinueButton: ScreenEnterPassKeySecretEvent
    data object HideLoadingDialog: ScreenEnterPassKeySecretEvent
}