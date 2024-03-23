package com.pass.word.session.navigation.screen.mainApp.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.TonCashDatabase
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.data.model.PasswordListContainer
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.ResponseStatus
import com.pass.word.session.utilits.StateBasicLoadingDialog
import com.pass.word.session.utilits.StateSelectedType
import com.pass.word.session.utilits.jsonStringToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScreenDetailComponent constructor(
    componentContext: ComponentContext,
    val passDetailModel: PasswordItemModel,
    private val onGoBack: () -> Unit,
    private val onGoEditScreen: (passDetailModel: PasswordItemModel, stateSelectedType: StateSelectedType) -> Unit,
    private val stateSelectedType: StateSelectedType
) : ComponentContext by componentContext {

    private var _stateOpenAlertDialog = MutableValue(false)
    val stateOpenAlertDialog: Value<Boolean> = _stateOpenAlertDialog

    private var _passwordItem: MutableValue<PasswordItemModel> =
        MutableValue(passDetailModel)
    val passwordItem: Value<PasswordItemModel> = _passwordItem

    private val seedPhrase = getParamsString(keyWalletSeed)

    private var _stateOpenDialogChoseType: MutableStateFlow<StateBasicLoadingDialog> =
        MutableStateFlow(StateBasicLoadingDialog.Hide)
    val stateOpenDialogChoseType get() = _stateOpenDialogChoseType

    fun getOneItem(databaseDriverFactory: DriverFactory) {
        if (stateSelectedType == StateSelectedType.LocalStorage) {
            val result = PersonalDatabase(databaseDriverFactory).getOneItemPass(passDetailModel.id)
            if (result != null) {
                _passwordItem.value = result
            }
        } else {
            val result = TonCashDatabase(databaseDriverFactory).getOneItemPass(passDetailModel.id)
            if (result != null) {
                _passwordItem.value = result
            }
        }
    }

    private fun deleteItem(databaseDriverFactory: DriverFactory) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // check type storage
                if (stateSelectedType == StateSelectedType.LocalStorage) {
                    PersonalDatabase(databaseDriverFactory).deleteOneItem(passDetailModel.id)
                    return@launch
                }
                _stateOpenDialogChoseType.update { StateBasicLoadingDialog.ShowLoading }
                val database = TonCashDatabase(databaseDriverFactory)

                val allItem = database.getAllPass().toMutableList()
                allItem.remove(passDetailModel)

                if (allItem.isEmpty()) {

                } else {
                    val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
                    if (seedPhrase != null) {
                        val resultInSend = WalletOperation(seedPhrase).sendNewItemPass(
                            PasswordListContainer(allItem)
                        )
                        if (resultInSend is ResponseStatus.Success) {
                            database.clearDatabase()
                            database.createPass(allItem)
                            _stateOpenDialogChoseType.update { StateBasicLoadingDialog.Hide }
                            onGoBack()
                        } else {
                            val messageError = (resultInSend as ResponseStatus.Error).errorMessage
                            _stateOpenDialogChoseType.update {
                                StateBasicLoadingDialog.Error(
                                    messageError
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _stateOpenDialogChoseType.update { StateBasicLoadingDialog.Error(e.message.toString()) }
            }
        }
    }

    fun onEvent(event: ScreenDetailEvent) {
        when (event) {
            is ScreenDetailEvent.ClickButtonBack -> {
                onGoBack()
            }

            is ScreenDetailEvent.ShowToast -> {

            }

            is ScreenDetailEvent.ChangeStateOpenedAlertDialog -> {
                _stateOpenAlertDialog.value = event.newState
            }

            is ScreenDetailEvent.DeleteItemPass -> {
                deleteItem(event.databaseDriverFactory)
            }

            is ScreenDetailEvent.EditItemPass -> {
                onGoEditScreen(passDetailModel, stateSelectedType)
            }
        }
    }


}