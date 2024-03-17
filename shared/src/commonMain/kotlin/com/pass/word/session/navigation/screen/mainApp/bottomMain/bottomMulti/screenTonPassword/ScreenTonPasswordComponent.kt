package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.subscribe
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.TonCashDatabase
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.StateBasicLoadingDialog
import com.pass.word.session.utilits.StatePassItemDisplay
import com.pass.word.session.utilits.StateSelectedType
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
        MutableStateFlow<StateBasicLoadingDialog>(StateBasicLoadingDialog.Hide)
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
        }
    }

    // This function read pass, in local storage
    private fun readLocalBd(databaseDriverFactory: DriverFactory) {
        try {
            _stateLoading.update { StateBasicLoadingDialog.ShowLoading }
            val database = PersonalDatabase(databaseDriverFactory).getAllPass()
            if (database.isEmpty()) {
                _statePassItemDisplay.update { StatePassItemDisplay.VisibleEmpty }
                return
            }
            _stateLoading.update { StateBasicLoadingDialog.Hide }
            _statePassItemDisplay.update { StatePassItemDisplay.VisibleItem(database) }
        } catch (e: Exception) {
            println("Erro pass screen - ${e.message}")
            _stateLoading.update { StateBasicLoadingDialog.Error(e.message.toString()) }
        }
    }

    // This function read pass, from ton
    private fun readTonPassItem(databaseDriverFactory: DriverFactory) {
        CoroutineScope(Dispatchers.IO).launch {
            _stateCallItem.update { false }
            val database = TonCashDatabase(databaseDriverFactory)
            readTonCashBd(database)
            delay(1000)
            when (val readResult = readPassInBlockchainTon()) {
                is ResultReadResultFromTonBlock.InSuccess -> {
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
                    _stateLoading.update { StateBasicLoadingDialog.ShowLoading }
                }

                is ResultReadResultFromTonBlock.InEmpty -> {
                    _stateLoading.update { StateBasicLoadingDialog.Hide }
                    _statePassItemDisplay.update { StatePassItemDisplay.VisibleEmpty }
                }

                is ResultReadResultFromTonBlock.InError -> {
                    _statePassItemDisplay.update { StatePassItemDisplay.VisibleNothing }
                    _stateLoading.update { StateBasicLoadingDialog.Error(readResult.message) }
                }
            }
        }
    }

    // This function read pass from Blockchain in Ton
    private suspend fun readPassInBlockchainTon(): ResultReadResultFromTonBlock {
        try {
            _stateLoading.update { StateBasicLoadingDialog.ShowLoading }
            val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
            return if (seedPhrase != null) {
                val walletOperation = WalletOperation(seedPhrase)
                val resultAddressPassChild = walletOperation.getItemPass()
                if (resultAddressPassChild != null) {
                    logging().i("readTonPassItem") { "resultAddressPassChild ${resultAddressPassChild}" }
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

    // This function read local cash, from ton pass
    private suspend fun readTonCashBd(database: TonCashDatabase) {
        val resultBd = database.getAllPass()
        if (resultBd.isEmpty()) {
            _statePassItemDisplay.update { StatePassItemDisplay.VisibleEmpty }
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