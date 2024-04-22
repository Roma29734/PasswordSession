package com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.primaryAction

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keySecretPassKey
import kotlinx.coroutines.flow.MutableStateFlow

class ScreenPrimaryKeyActionComponent (
    componentContext: ComponentContext,
    private val navToBackScreen: () -> Unit,
    private val navToNextScreen: (String) -> Unit,
) : ComponentContext by componentContext {


    private val passKeyPhrase = getParamsString(keySecretPassKey)

    private var _passKeyPhraseState: MutableStateFlow<String> =  MutableStateFlow(passKeyPhrase?: "")
    val passKeyPhraseState get() = _passKeyPhraseState

    fun onBack() {
        navToBackScreen()
    }

    fun onNext() {
        navToNextScreen(_passKeyPhraseState.value)
    }

}