package com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterSeedPhrase

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.listToStringJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class ScreenEnterSeedPhraseComponent(
    componentContext: ComponentContext,
    private val navToNextScreen: (
        walletAddress: String,
        jsonSeedPhrase: String
    ) -> Unit,
    private val navToBackScreen: () -> Unit,
) : ComponentContext by componentContext {

    private var _stateEnableButtonNext = MutableStateFlow(false)
    val stateEnableButtonNext get() = _stateEnableButtonNext

    private var _stateSeedText = MutableStateFlow(mutableStateOf(List(24) { "" }))
    val stateSeedText get() = _stateSeedText

    private var _stateOpenDialogLoading = MutableStateFlow(false)
    val stateOpenDialogLoading get() = _stateOpenDialogLoading

    private var _stateLoadingAlert =
        MutableStateFlow<StateLoadSeedPhrase>(StateLoadSeedPhrase.InLoadState)
    val stateLoadingAlert
        get() = _stateLoadingAlert.asStateFlow()

    private var stateSeedPhrase = MutableStateFlow("")
    private var stateAddressWallet = MutableStateFlow("")

    fun onEvent(event: ScreenEnterSeedPhraseEvent) {
        when (event) {
            is ScreenEnterSeedPhraseEvent.ChangeHandlerInputItem -> {

                val newBera = _stateSeedText.value.value.toMutableList()
                newBera[event.posItem] = event.newItem
                _stateSeedText.value.value = newBera
                logging().i("enterSeedPhrase") { _stateSeedText.value.value }

                if(_stateSeedText.value.value[0] == "testNet123") {
                    _stateSeedText.value.value = testNetWalletAddress
                    _stateEnableButtonNext.update { true }
                }

                if (_stateSeedText.value.value.all { it.isNotBlank() }) {
                    _stateEnableButtonNext.update { true }
                } else {
                    _stateEnableButtonNext.update { false }
                }
            }

            is ScreenEnterSeedPhraseEvent.NavToBack -> {
                navToBackScreen()
            }

            is ScreenEnterSeedPhraseEvent.SendSeed -> {
                _stateOpenDialogLoading.update { true }
                _stateLoadingAlert.update { StateLoadSeedPhrase.InLoadState }
                CoroutineScope(Dispatchers.IO).launch {
                    val resultJsonSeedPhrase = listToStringJson(_stateSeedText.value.value)
                    logging().i("enterSeedPhrase") { "resultJson - $resultJsonSeedPhrase" }
                    val result = WalletOperation(_stateSeedText.value.value).getWalletAddress()
                    logging().i("enterSeedPhrase") { "result address - $result" }
                    if (result != null) {
                        stateSeedPhrase.update { resultJsonSeedPhrase }
                        stateAddressWallet.update { result }
                        _stateLoadingAlert.update { StateLoadSeedPhrase.InSuccess }
                    }
                }
            }

            is ScreenEnterSeedPhraseEvent.NavToNextScreen -> {
                navToNextScreen(
                    stateAddressWallet.value,
                    stateSeedPhrase.value
                )
                _stateOpenDialogLoading.update { false }
            }
        }
    }

    val testNetWalletAddress = listOf(
        "alert",
        "grain",
        "ethics",
        "drum",
        "surge",
        "vocal",
        "nation",
        "empower",
        "canyon",
        "guitar",
        "project",
        "diet",
        "field",
        "afford",
        "stumble",
        "joy",
        "solve",
        "inform",
        "amused",
        "angle",
        "fantasy",
        "move",
        "transfer",
        "actress"
    )
}