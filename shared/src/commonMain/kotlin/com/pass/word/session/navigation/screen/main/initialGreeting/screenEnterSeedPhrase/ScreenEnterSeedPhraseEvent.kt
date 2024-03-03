package com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterSeedPhrase


interface ScreenEnterSeedPhraseEvent {
    data class ChangeHandlerInputItem(val newItem: String, val posItem: Int) : ScreenEnterSeedPhraseEvent
    data object NavToBack : ScreenEnterSeedPhraseEvent
    data object SendSeed: ScreenEnterSeedPhraseEvent
    data object NavToNextScreen: ScreenEnterSeedPhraseEvent
}

sealed class StateLoadSeedPhrase {
    data object InLoadState : StateLoadSeedPhrase()
    data object InSuccess : StateLoadSeedPhrase()
}