package com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keySecretPassKey
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.data.putToParams
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.ResultReadResultFromTonBlock
import com.pass.word.session.utilits.StateBasicDialog
import com.pass.word.session.utilits.jsonStringToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScreenEnterPassKeySecretComponent(
    componentContext: ComponentContext,
    private val navToNextScreen: () -> Unit,
    private val navToBackScreen: () -> Unit,
) : ComponentContext by componentContext {

    private var _statePassKeySecret = MutableStateFlow("")
    val statePassKeySecret get() = _statePassKeySecret

    private var _stateEnableButtonNext = MutableStateFlow(false)
    val stateEnableButtonNext get() = _stateEnableButtonNext

    // state progress loading (state showed dialog)
    private var _stateLoading =
        MutableStateFlow<StateBasicDialog>(StateBasicDialog.Hide)
    val stateLoading = _stateLoading

    private var _warningText = MutableStateFlow<String?>(null)
    val warningText get() = _warningText

    private val seedPhrase = getParamsString(keyWalletSeed)

    // superSecretPhrqweerqeqase
    fun onEvent(event: ScreenEnterPassKeySecretEvent) {
        when (event) {
            is ScreenEnterPassKeySecretEvent.NavToBack -> {
                navToBackScreen()
            }

            is ScreenEnterPassKeySecretEvent.ClickContinueButton -> {
                _stateLoading.update { StateBasicDialog.Show }
                if (_statePassKeySecret.value.length >= 14) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
                        if (seedPhrase != null) {
                            when (WalletOperation(seedPhrase).getItemPass(_statePassKeySecret.value)) {
                                is ResultReadResultFromTonBlock.InEmpty -> {
                                    _stateLoading.update { StateBasicDialog.Hide }
                                    _statePassKeySecret.value.putToParams(keySecretPassKey)
                                    navToNextScreen()
                                }

                                is ResultReadResultFromTonBlock.InSuccess -> {
                                    _stateLoading.update { StateBasicDialog.Hide }
                                    _statePassKeySecret.value.putToParams(keySecretPassKey)
                                    navToNextScreen()
                                }

                                is ResultReadResultFromTonBlock.InError -> {
                                    _stateLoading.update { StateBasicDialog.Error("The phrase already exists, the one you entered is incorrect") }
                                }
                            }
                        }
                    }
                } else {
                    _stateLoading.update { StateBasicDialog.Error("Incorrect password length") }
                }
            }

            is ScreenEnterPassKeySecretEvent.UpdateStateItemText -> {
                _statePassKeySecret.update { event.newText }
                if (_statePassKeySecret.value.length >= 14) {
                    _stateEnableButtonNext.update { true }
                    warningText.update { null }
                } else {
                    warningText.update { "The length must be at least 16 characters" }
                    _stateEnableButtonNext.update { false }
                }
            }

            is ScreenEnterPassKeySecretEvent.HideLoadingDialog -> {
                _stateLoading.update { StateBasicDialog.Hide }
            }
        }
    }
}