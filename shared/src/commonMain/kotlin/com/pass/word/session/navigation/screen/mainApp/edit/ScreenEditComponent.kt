package com.pass.word.session.navigation.screen.mainApp.edit

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
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.ScreenAddMultiPasswordEvent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.StateAddDialog
import com.pass.word.session.tonCore.contract.wallet.WalletOperation
import com.pass.word.session.utilits.EventDispatcher
import com.pass.word.session.utilits.ResponseStatus
import com.pass.word.session.utilits.StateBasicLoadingDialog
import com.pass.word.session.utilits.StateBasicResult
import com.pass.word.session.utilits.StateSelectedType
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

class ScreenEditComponent constructor(
    componentContext: ComponentContext,
    val passDetailModel: PasswordItemModel,
    private val onGoBack: () -> Unit,
    private val stateSelectedType: StateSelectedType
) : ComponentContext by componentContext {

    private var _textTitle = MutableValue(passDetailModel.nameItemPassword)
    val textTitle: Value<String> = _textTitle

    private var _textEmailOrUserName = MutableValue(passDetailModel.emailOrUserName)
    val textEmailOrUserName: Value<String> = _textEmailOrUserName

    private var _textPassword = MutableValue(passDetailModel.passwordItem)
    val textPassword: Value<String> = _textPassword

    private var _textUrl = MutableValue(passDetailModel.urlSite ?: "")
    val textUrl: Value<String> = _textUrl

    private var _textDescriptions = MutableValue(passDetailModel.descriptions ?: "")
    val textDescriptions: Value<String> = _textDescriptions

    val showSnackBarDispatcher = EventDispatcher<String>()

    private var _stateOpenDialogChoseType: MutableStateFlow<StateBasicLoadingDialog> =
        MutableStateFlow(StateBasicLoadingDialog.Hide)
    val stateOpenDialogChoseType get() = _stateOpenDialogChoseType
    private val seedPhrase = getParamsString(keyWalletSeed)

    fun onEvent(event: ScreenEditEvent) {
        when (event) {
            is ScreenEditEvent.ClickButtonBack -> {
                onGoBack();
            }

            is ScreenEditEvent.UpdateTextTitle -> {
                _textTitle.value = event.textTitle
            }

            is ScreenEditEvent.UpdateTextEmailORUserName -> {
                _textEmailOrUserName.value = event.textEmailOrUserName
            }

            is ScreenEditEvent.UpdateTextPassword -> {
                _textPassword.value = event.textPassword
            }

            is ScreenEditEvent.UpdateTextUrl -> {
                _textUrl.value = event.textUrl
            }

            is ScreenEditEvent.UpdateTextDescriptions -> {
                _textDescriptions.value = event.textDescriptions
            }

            is ScreenEditEvent.ClickButtonUpdate -> {
                if (textTitle.value.isEmpty() || textEmailOrUserName.value.isEmpty() || textPassword.value.isEmpty()) {
                    showSnackBarDispatcher.dispatch("The first three fields must be filled in")
                } else {
                    if (stateSelectedType == StateSelectedType.LocalStorage) {
                        updateInLocalStorage(event.databaseDriverFactory)
                        return
                    }
                    if (stateSelectedType == StateSelectedType.TonStorage) {
                        updateInTonStorage(event.databaseDriverFactory)
                    }
                }
            }
            is ScreenEditEvent.CloseAllAlert -> {
                _stateOpenDialogChoseType.update { StateBasicLoadingDialog.Hide }
            }
        }
    }


    private fun updateInLocalStorage(databaseDriverFactory: DriverFactory) {
        val localDate = getThisLocalTime()
        val model = PasswordItemModel(
            id = passDetailModel.id,
            nameItemPassword = textTitle.value,
            emailOrUserName = textEmailOrUserName.value,
            passwordItem = textPassword.value,
            changeData = localDate,
            urlSite = textUrl.value.onCheckValidation(),
            descriptions = textDescriptions.value.onCheckValidation()
        )
        PersonalDatabase(databaseDriverFactory).updatePassItem(model)
        onGoBack()
    }

    private fun updateInTonStorage(databaseDriverFactory: DriverFactory) {
        CoroutineScope(Dispatchers.IO).launch {
            _stateOpenDialogChoseType.update { StateBasicLoadingDialog.ShowLoading }
            val localDate = getThisLocalTime()
            val model = PasswordItemModel(
                id = passDetailModel.id,
                nameItemPassword = textTitle.value,
                emailOrUserName = textEmailOrUserName.value,
                passwordItem = textPassword.value,
                changeData = localDate,
                urlSite = textUrl.value.onCheckValidation(),
                descriptions = textDescriptions.value.onCheckValidation()
            )

            if(model == passDetailModel ) {
                _stateOpenDialogChoseType.update { StateBasicLoadingDialog.Hide }
                onGoBack()
                return@launch
            }

            val database = TonCashDatabase(databaseDriverFactory)
            database.updatePassItem(model)
            val seedPhrase = seedPhrase?.let { jsonStringToList(it) }
            if(seedPhrase != null) {
                val itemResult = database.getAllPass()
                when(val resultInSend = WalletOperation(seedPhrase).sendNewItemPass(PasswordListContainer(itemResult), null)) {
                    is StateBasicResult.InSuccess -> {
                        _stateOpenDialogChoseType.update { StateBasicLoadingDialog.Hide }
                        showSnackBarDispatcher.dispatch("Password a success created")
                        onGoBack()
                    }
                    is StateBasicResult.InError -> {
                        _stateOpenDialogChoseType.update { StateBasicLoadingDialog.Error(resultInSend.errorCode.convertToMessageAndCode()) }
                    }
                }
            }
        }
    }
}