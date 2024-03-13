package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent.ScreenAddPasswordStateEvent
import com.pass.word.session.utilits.EventDispatcher

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

    val eventDispatcher = EventDispatcher<String>()

    fun onEvent(eventAdd: ScreenAddMultiPasswordEvent) {
        when (eventAdd) {
            is ScreenAddMultiPasswordEvent.ClickButtonAddNewState -> {
                if (textTitle.value.isEmpty() || textEmailOrUserName.value.isEmpty() || textPassword.value.isEmpty()) {
                    eventDispatcher.dispatch("Not all fields are filled in")
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
        }
    }

}