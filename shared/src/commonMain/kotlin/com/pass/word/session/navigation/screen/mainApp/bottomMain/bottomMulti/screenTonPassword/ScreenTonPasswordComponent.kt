package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.jsonStringToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging

class ScreenTonPasswordComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {


    private var _stateLoading =
        MutableStateFlow<LoadingTonPassItemState>(LoadingTonPassItemState.InLoading)
    val stateLoading = _stateLoading

    private val seedPhrase = getParamsString(keyWalletSeed)

    fun readTonPassItem() {
        val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
        if (seedPhrase != null) {
            val walletOperation = WalletOperation(seedPhrase)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val resultAddressPassChild = walletOperation.getItemPass()
                    if (resultAddressPassChild != null) {
                        _stateLoading.update {
                            LoadingTonPassItemState.InSuccess(
                                resultAddressPassChild.passwordList
                            )
                        }
                    } else {
                        _stateLoading.update { LoadingTonPassItemState.InEmpty }
                    }

                } catch (e: Exception) {
                    _stateLoading.update { LoadingTonPassItemState.InError(e.message.toString()) }
                }
            }
        }
    }
}