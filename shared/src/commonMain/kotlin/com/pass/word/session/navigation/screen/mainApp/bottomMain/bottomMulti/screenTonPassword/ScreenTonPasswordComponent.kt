package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.subscribe
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.TonCashDatabase
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.jsonStringToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class ScreenTonPasswordComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {


    private var _stateLoading =
        MutableStateFlow<LoadingTonPassItemState>(LoadingTonPassItemState.IsSuccess)
    val stateLoading = _stateLoading

    private val seedPhrase = getParamsString(keyWalletSeed)

    private var _passwordListItem = MutableStateFlow<List<PasswordItemModel>?>(null)
    val passwordListItem get() = _passwordListItem

    private var _stateCallItem = MutableStateFlow<Boolean>(false)
    val stateCallItem get() = _stateCallItem

    fun onEvent(event: ScreenTonPasswordEvent) {
        when (event) {
            is ScreenTonPasswordEvent.ReadBdItem -> {
                readPassItem(event.databaseDriverFactory)
            }
        }
    }

    private fun readPassItem(databaseDriverFactory: DriverFactory) {
        CoroutineScope(Dispatchers.IO).launch {
            _stateCallItem.update { false }
            val database = TonCashDatabase(databaseDriverFactory)
            readLocalBd(database)
            delay(1000)
            when (val readResult = readTonPassItem()) {
                is ResultReadResultFromTonBlock.InSuccess -> {
                    val listOne = _passwordListItem.value?.map { it.copy(id = 0) }
                    val listTwo = readResult.itemPass.map { it.copy(id = 0) }
                    logging().i("ScreenTonPasswordComponent") { "listOne ${listOne}" }
                    logging().i("ScreenTonPasswordComponent") { "listTwo ${listTwo}" }

                    if (listOne == listTwo) {
                        logging().i("ScreenTonPasswordComponent") { "readPassItem item equal" }
                    } else {
                        database.clearDatabase()
                        database.createPass(readResult.itemPass)
                        readLocalBd(database)
                    }
                    _stateLoading.update { LoadingTonPassItemState.IsSuccess }
                }

                is ResultReadResultFromTonBlock.InEmpty -> {
                    _stateLoading.update { LoadingTonPassItemState.InEmpty }
                }

                is ResultReadResultFromTonBlock.InError -> {
                    _stateLoading.update { LoadingTonPassItemState.InError(readResult.message) }
                }
            }
        }
    }

    private suspend fun readTonPassItem(): ResultReadResultFromTonBlock {
        try {
            _stateLoading.update { LoadingTonPassItemState.InLoading }
            val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
            return if (seedPhrase != null) {
                val walletOperation = WalletOperation(seedPhrase)
                val resultAddressPassChild = walletOperation.getItemPass()
                if (resultAddressPassChild != null) {
                    ResultReadResultFromTonBlock.InSuccess(resultAddressPassChild.passwordList)
                } else {
                    ResultReadResultFromTonBlock.InEmpty
                }
            } else {
                ResultReadResultFromTonBlock.InEmpty
            }
        } catch (e: Exception) {
            return ResultReadResultFromTonBlock.InError(e.message.toString())
        }
    }

    private suspend fun readLocalBd(database: TonCashDatabase) {
        val resultBd = database.getAllPass()
        if (resultBd.isEmpty()) {
            _stateLoading.update { LoadingTonPassItemState.InEmpty }
        } else {
            _passwordListItem.update { resultBd }
        }
    }

    init {
        lifecycle.subscribe(
            object : Lifecycle.Callbacks {
                override fun onCreate() {
                    /* Component created */
                }
            }
        )

        lifecycle.subscribe(
            onCreate = {
                _stateCallItem.update { true }
            }
        )

    }

}