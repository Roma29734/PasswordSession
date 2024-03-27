package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword

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
import com.pass.word.session.utilits.EventDispatcher
import com.pass.word.session.utilits.ResponseStatus
import com.pass.word.session.utilits.StateBasicResult
import com.pass.word.session.utilits.convertToMessageAndCode
import com.pass.word.session.utilits.getThisLocalTime
import com.pass.word.session.utilits.jsonStringToList
import com.pass.word.session.utilits.onCheckValidation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class ScreenAddMultiPasswordComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private var _textTitle = MutableValue("")
    val textTitle: Value<String> = _textTitle

    private var _textEmailOrUserName = MutableValue("")
    val textEmailOrUserName: Value<String> = _textEmailOrUserName

    private var _textPassword = MutableValue("")
    val textPassword: Value<String> = _textPassword

    private var _textUrl = MutableValue("")
    val textUrl: Value<String> = _textUrl

    private var _textDescriptions = MutableValue("")
    val textDescriptions: Value<String> = _textDescriptions

    val showSnackBarDispatcher = EventDispatcher<String>()

    private val seedPhrase = getParamsString(keyWalletSeed)

    private var _stateOpenDialogChoseType: MutableStateFlow<StateAddDialog> =
        MutableStateFlow(StateAddDialog.Hide)
    val stateOpenDialogChoseType get() = _stateOpenDialogChoseType

    fun onEvent(eventAdd: ScreenAddMultiPasswordEvent) {
        when (eventAdd) {
            is ScreenAddMultiPasswordEvent.ClickButtonAddNewState -> {
                if (textTitle.value.isEmpty() || textEmailOrUserName.value.isEmpty() || textPassword.value.isEmpty()) {
                    showSnackBarDispatcher.dispatch("Not all fields are filled in")
                    return
                }
                _stateOpenDialogChoseType.update {
                    StateAddDialog.Show({
                        savedTonStorageType(databaseDriverFactory = eventAdd.databaseDriverFactory)
                    }, {
                        savedLocalStorageType(databaseDriverFactory = eventAdd.databaseDriverFactory)
                    })
                }
            }

            is ScreenAddMultiPasswordEvent.UpdateTextTitle -> {
                _textTitle.value = eventAdd.textTitle
            }

            is ScreenAddMultiPasswordEvent.UpdateTextEmailORUserName -> {
                _textEmailOrUserName.value = eventAdd.textEmailOrUserName
            }

            is ScreenAddMultiPasswordEvent.UpdateTextPassword -> {
                _textPassword.value = eventAdd.textPassword
            }

            is ScreenAddMultiPasswordEvent.UpdateTextUrl -> {
                _textUrl.value = eventAdd.textUrl
            }

            is ScreenAddMultiPasswordEvent.UpdateTextDescriptions -> {
                _textDescriptions.value = eventAdd.textDescriptions
            }

            is ScreenAddMultiPasswordEvent.CloseAllAlert -> {
                _stateOpenDialogChoseType.update { StateAddDialog.Hide }
            }

        }
    }

    private fun savedTonStorageType(
        databaseDriverFactory: DriverFactory
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            _stateOpenDialogChoseType.update { StateAddDialog.ShowLoading }
            val localDate = getThisLocalTime()
            val model = PasswordItemModel(
                nameItemPassword = textTitle.value,
                emailOrUserName = textEmailOrUserName.value,
                passwordItem = textPassword.value,
                changeData = localDate,
                urlSite = textUrl.value.onCheckValidation(),
                descriptions = textDescriptions.value.onCheckValidation(),
                id = 1,
            )
            val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
            if (seedPhrase != null) {
                val database = TonCashDatabase(databaseDriverFactory)
                val itemList = database.getAllPass().toMutableList()
                itemList.add(model)
                logging().i("walletOperation") { "sendNewItemPass itemList $itemList" }
                when(val resultInSend = WalletOperation(seedPhrase).sendNewItemPass(PasswordListContainer(itemList), null)) {
                    is StateBasicResult.InSuccess -> {
                        database.clearDatabase()
                        database.createPass(itemList)
                        _stateOpenDialogChoseType.update { StateAddDialog.Hide }
                        _textTitle.value = ""
                        _textEmailOrUserName.value = ""
                        _textPassword.value = ""
                        _textUrl.value = ""
                        _textDescriptions.value = ""
                        showSnackBarDispatcher.dispatch("Password a success created")
                    }
                    is StateBasicResult.InError -> {
                        _stateOpenDialogChoseType.update { StateAddDialog.Error(resultInSend.errorCode.convertToMessageAndCode()) }
                    }
                }
            }
        }
    }

    private fun savedLocalStorageType(databaseDriverFactory: DriverFactory) {
        addPassToDataBass(databaseDriverFactory = databaseDriverFactory)
        _stateOpenDialogChoseType.update { StateAddDialog.Hide }
    }

    private fun addPassToDataBass(
        databaseDriverFactory: DriverFactory
    ) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val localDate = getThisLocalTime()
                val model = PasswordItemModel(
                    nameItemPassword = textTitle.value,
                    emailOrUserName = textEmailOrUserName.value,
                    passwordItem = textPassword.value,
                    changeData = localDate,
                    urlSite = textUrl.value.onCheckValidation(),
                    descriptions = textDescriptions.value.onCheckValidation(),
                    id = 1,
                )
                val database = PersonalDatabase(databaseDriverFactory)
                database.createPass(listOf(model))
                _textTitle.value = ""
                _textEmailOrUserName.value = ""
                _textPassword.value = ""
                _textUrl.value = ""
                _textDescriptions.value = ""
                showSnackBarDispatcher.dispatch("Password a success created")
            }
        } catch (e: Exception) {
            logging().i("ScreenAddMultiPasswordComponent") { "addPassToDataBass error ${e.message}" }
            showSnackBarDispatcher.dispatch("Password a success created")
        }
    }
}