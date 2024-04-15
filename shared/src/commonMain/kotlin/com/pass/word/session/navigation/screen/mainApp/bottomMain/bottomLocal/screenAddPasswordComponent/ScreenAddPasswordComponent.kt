package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.utilits.getThisLocalTime
import com.pass.word.session.utilits.onCheckValidation

class ScreenAddPasswordComponent constructor(
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

    private val listenersPassCreate = mutableListOf<(message: String, complete: Boolean) -> Unit>()

    fun subscribeListenerPassCreate(listener: (message: String, complete: Boolean) -> Unit) {
        listenersPassCreate.add(listener)
    }

    fun unsubscribeListenerPassCreate(listener: (message: String, complete: Boolean) -> Unit) {
        listenersPassCreate.remove(listener)
    }

    private fun pluckPassCreate(message: String, complete: Boolean) {
        listenersPassCreate.forEach { it.invoke(message, complete) }
    }

    private fun addPassToDataBass(
        databaseDriverFactory: DriverFactory
    ) {
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
        pluckPassCreate("Password a success created", true)
    }

    fun onEvent(eventAdd: ScreenAddPasswordStateEvent) {
        when (eventAdd) {
            is ScreenAddPasswordStateEvent.ClickButtonAddNewState -> {
                if (textTitle.value.isEmpty() || textEmailOrUserName.value.isEmpty() || textPassword.value.isEmpty()) {
                    pluckPassCreate("Not all fields are filled in", false)
                } else {
                    addPassToDataBass(eventAdd.databaseDriverFactory)
                }
            }

            is ScreenAddPasswordStateEvent.UpdateTextTitle -> {
                _textTitle.value = eventAdd.textTitle
            }

            is ScreenAddPasswordStateEvent.UpdateTextEmailORUserName -> {
                _textEmailOrUserName.value = eventAdd.textEmailOrUserName
            }

            is ScreenAddPasswordStateEvent.UpdateTextPassword -> {
                _textPassword.value = eventAdd.textPassword
            }

            is ScreenAddPasswordStateEvent.UpdateTextUrl -> {
                _textUrl.value = eventAdd.textUrl
            }

            is ScreenAddPasswordStateEvent.UpdateTextDescriptions -> {
                _textDescriptions.value = eventAdd.textDescriptions
            }
        }
    }
}