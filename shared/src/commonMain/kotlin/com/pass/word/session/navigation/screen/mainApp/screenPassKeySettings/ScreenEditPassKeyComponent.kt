package com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.StateBasicResult
import com.pass.word.session.utilits.StateTwosItemDialog
import com.pass.word.session.utilits.jsonStringToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScreenEditPassKeyComponent(
    componentContext: ComponentContext,
    private val navToBackScreen: () -> Unit,
    private val navToNextScreen: () -> Unit,
    private val passKeyPhrase: String
) : ComponentContext by componentContext {

    private var _statePassKeySecret = MutableStateFlow("")
    val statePassKeySecret get() = _statePassKeySecret

    private var _stateEnableButtonNext = MutableStateFlow(false)
    val stateEnableButtonNext get() = _stateEnableButtonNext

    private var _warningText = MutableStateFlow<String?>(null)
    val warningText get() = _warningText

    private var _stateShowedDialog = MutableStateFlow<StateTwosItemDialog>(StateTwosItemDialog.Hide)
    val stateShowedDialog get() = _stateShowedDialog

    private val seedPhrase = getParamsString(keyWalletSeed)?.let { jsonStringToList(it) }

    fun onEvent(event: ScreenPassKeySettingsEvent) {
        when (event) {
            is ScreenPassKeySettingsEvent.ChangeItemText -> {
                _statePassKeySecret.update { event.newItemText }

                if (_statePassKeySecret.value.length >= 16) {
                    _stateEnableButtonNext.update { true }
                    _warningText.update { null }
                } else {
                    _warningText.update { "The length must be at least 16 characters" }
                    _stateEnableButtonNext.update { false }
                }
            }

            is ScreenPassKeySettingsEvent.OnBack -> {
                navToBackScreen()
            }

            is ScreenPassKeySettingsEvent.OnClickButtonChange -> {
                _stateShowedDialog.update { StateTwosItemDialog.ShowOneDialog }
            }

            is ScreenPassKeySettingsEvent.OnNext -> {
                if (seedPhrase != null) {
                    _stateShowedDialog.update { StateTwosItemDialog.ShowTwoDialog }
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val walletOperation = WalletOperation(seedPhrase)
                            val result = walletOperation.sendToChangePassKey(
                                passKeyPhrase,
                                statePassKeySecret.value
                            )
                            when (result) {
                                is StateBasicResult.InSuccess -> {
                                    _stateShowedDialog.update { StateTwosItemDialog.Hide }
                                    navToNextScreen()
                                }

                                is StateBasicResult.InError -> {
                                    _stateShowedDialog.update { StateTwosItemDialog.Error("") }
                                }
                            }
                        } catch (e: Exception) {
                            _stateShowedDialog.update { StateTwosItemDialog.Error("") }
                        }
                    }
                }
            }
            is ScreenPassKeySettingsEvent.HideDialog -> {
                _stateShowedDialog.update { StateTwosItemDialog.Hide }
            }
        }
    }
}