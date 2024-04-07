package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.subscribe
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.TonCashDatabase
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keySecretPassKey
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.ResultReadResultFromTonBlock
import com.pass.word.session.utilits.StateBasicDialog
import com.pass.word.session.utilits.StatePassItemDisplay
import com.pass.word.session.utilits.StateSelectedType
import com.pass.word.session.utilits.StateStatusBar
import com.pass.word.session.utilits.conversionToMessage
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
    componentContext: ComponentContext,
    private val onNavigateToDetailComponent: (PasswordItemModel, StateSelectedType) -> Unit
) : ComponentContext by componentContext {

    // state progress loading (state showed dialog)
    private var _stateLoading =
        MutableStateFlow<StateBasicDialog>(StateBasicDialog.Hide)
    val stateLoading = _stateLoading

    private val seedPhrase = getParamsString(keyWalletSeed)

    // state show item pass
    private var _statePassItemDisplay =
        MutableStateFlow<StatePassItemDisplay>(StatePassItemDisplay.VisibleNothing)
    val statePassItemDisplay get() = _statePassItemDisplay

    // state of call item
    private var _stateCallItem = MutableStateFlow(false)
    val stateCallItem get() = _stateCallItem

    // state selected type of storage
    private var _stateSelectedTypeStorage: MutableStateFlow<StateSelectedType> =
        MutableStateFlow(StateSelectedType.TonStorage)
    val stateSelectedTypeStorage get() = _stateSelectedTypeStorage

    // state visible status bar of state
    private var _stateVisibleStatusBar: MutableStateFlow<StateStatusBar> =
        MutableStateFlow(StateStatusBar.Hide)
    val stateVisibleStatusBar get() = _stateVisibleStatusBar

    // this function handle event
    fun onEvent(event: ScreenTonPasswordEvent) {
        when (event) {
            is ScreenTonPasswordEvent.ReadBdItem -> {
                readTonPassItem(event.databaseDriverFactory)
            }

            is ScreenTonPasswordEvent.UpdateSelectedType -> {
                _stateSelectedTypeStorage.update { event.newType }
                if (_stateSelectedTypeStorage.value == StateSelectedType.TonStorage) {
                    readTonPassItem(event.databaseDriverFactory)
                } else {
                    readLocalBd(event.databaseDriverFactory)
                }
            }

            is ScreenTonPasswordEvent.ClickToItem -> {
                onNavigateToDetailComponent(event.model, _stateSelectedTypeStorage.value)
            }

            is ScreenTonPasswordEvent.ReadCashPass -> {
                if (_stateSelectedTypeStorage.value == StateSelectedType.TonStorage) {
                    val database = TonCashDatabase(event.databaseDriverFactory)
                    CoroutineScope(Dispatchers.IO).launch {
                        readTonCashBd(database)
                    }
                } else {
                    readLocalBd(event.databaseDriverFactory)
                }
            }

            is ScreenTonPasswordEvent.HideDialog -> {
                _stateLoading.update { StateBasicDialog.Hide }
            }
        }
    }

    // This function read pass, in local storage
    private fun readLocalBd(databaseDriverFactory: DriverFactory) {
        try {
            val database = PersonalDatabase(databaseDriverFactory).getAllPass()
            if (database.isEmpty()) {
                _statePassItemDisplay.update { StatePassItemDisplay.VisibleMessage("You don't have any saved passwords") }
                return
            }
            _stateLoading.update { StateBasicDialog.Hide }
            _statePassItemDisplay.update { StatePassItemDisplay.VisibleItem(database) }
        } catch (e: Exception) {
            println("Erro pass screen - ${e.message}")
            _stateLoading.update { StateBasicDialog.Error(e.message.toString()) }
        }
    }

    // This function read pass, from ton
    private fun readTonPassItem(databaseDriverFactory: DriverFactory) {
        CoroutineScope(Dispatchers.IO).launch {
            _stateCallItem.update { false }
            _stateLoading.update { StateBasicDialog.Show }
            val database = TonCashDatabase(databaseDriverFactory)
            readTonCashBd(database)
            delay(200)
            when (val readResult = readPassInBlockchainTon()) {
                is ResultReadResultFromTonBlock.InSuccess -> {
                    _stateVisibleStatusBar.update { StateStatusBar.Hide }
                    if (_statePassItemDisplay.value is StatePassItemDisplay.VisibleItem) {
                        val listOne =
                            (_statePassItemDisplay.value as StatePassItemDisplay.VisibleItem).passItem?.map {
                                it.copy(id = 0)
                            }
                        val listTwo = readResult.itemPass.map { it.copy(id = 0) }
                        logging().i("ScreenTonPasswordComponent") { "listOne ${listOne}" }
                        logging().i("ScreenTonPasswordComponent") { "listTwo ${listTwo}" }

                        if (listOne == listTwo) {
                            logging().i("ScreenTonPasswordComponent") { "readPassItem item equal" }
                        } else {
                            database.clearDatabase()
                            database.createPass(readResult.itemPass)
                            readTonCashBd(database)
                        }
                        _stateLoading.update { StateBasicDialog.Hide }
                    } else {
                        database.clearDatabase()
                        database.createPass(readResult.itemPass)
                        readTonCashBd(database)
                        _statePassItemDisplay.update { StatePassItemDisplay.VisibleItem(readResult.itemPass) }
                        _stateLoading.update { StateBasicDialog.Hide }
                    }
                }

                is ResultReadResultFromTonBlock.InEmpty -> {
                    _stateVisibleStatusBar.update { StateStatusBar.Hide }
                    if (database.getAllPass().isNotEmpty()) {
                        database.clearDatabase()
                    }
                    _stateLoading.update { StateBasicDialog.Hide }
                    _statePassItemDisplay.update { StatePassItemDisplay.VisibleMessage("You don't have any saved passwords") }
                }

                is ResultReadResultFromTonBlock.InError -> {

                    _stateVisibleStatusBar.update { StateStatusBar.Show("${readResult.errorCode} ${readResult.errorCode.conversionToMessage()}") }
                    _stateLoading.update { StateBasicDialog.Error(readResult.message) }
                }
            }
        }
    }

    // This function read pass from Blockchain in Ton
    private suspend fun readPassInBlockchainTon(): ResultReadResultFromTonBlock {
        val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
        val keyPhrase = getParamsString(keySecretPassKey)
        return if (seedPhrase != null) {
            if (keyPhrase != null) {
                val walletOperation = WalletOperation(seedPhrase)
                return walletOperation.getItemPass(keyPhrase)
            } else {
                ResultReadResultFromTonBlock.InEmpty
            }
        } else {
            ResultReadResultFromTonBlock.InEmpty
        }
    }

    // This function read local cash, from ton pass
    private suspend fun readTonCashBd(database: TonCashDatabase) {
        val resultBd = database.getAllPass()
        if (resultBd.isEmpty()) {
            _statePassItemDisplay.update { StatePassItemDisplay.VisibleMessage("You don't have any saved passwords") }
        } else {
            _statePassItemDisplay.update { StatePassItemDisplay.VisibleItem(resultBd) }
        }
    }

    init {
        lifecycle.subscribe(
            onCreate = {
                // update state call to readTonPassItem()
                _stateCallItem.update { true }
            }
        )
    }

}