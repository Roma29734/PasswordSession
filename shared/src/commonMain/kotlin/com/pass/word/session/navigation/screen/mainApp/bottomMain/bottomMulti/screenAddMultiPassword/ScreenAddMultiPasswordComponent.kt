package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.utilits.EventDispatcher
import com.pass.word.session.utilits.getThisLocalTime
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
): ComponentContext by componentContext {

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


    private var _stateOpenDialogChoseType: MutableStateFlow<StateAlertDialog> = MutableStateFlow(StateAlertDialog.Hide)
    val stateOpenDialogChoseType get() = _stateOpenDialogChoseType

    fun onEvent(eventAdd: ScreenAddMultiPasswordEvent) {
        when (eventAdd) {
            is ScreenAddMultiPasswordEvent.ClickButtonAddNewState -> {
                if (textTitle.value.isEmpty() || textEmailOrUserName.value.isEmpty() || textPassword.value.isEmpty()) {
                    showSnackBarDispatcher.dispatch("Not all fields are filled in")
                    return
                }
                _stateOpenDialogChoseType.update { StateAlertDialog.Show({
                    savedTonStorageType()
                }, {
                    savedLocalStorageType(databaseDriverFactory = eventAdd.databaseDriverFactory)
                }) }
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
        }
    }

    private fun savedTonStorageType() {
        _stateOpenDialogChoseType.update { StateAlertDialog.Hide }
    }

    private fun savedLocalStorageType(databaseDriverFactory: DriverFactory) {
        addPassToDataBass(databaseDriverFactory = databaseDriverFactory)
        _stateOpenDialogChoseType.update { StateAlertDialog.Hide }
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