package com.pass.word.session.navigation.screen.main.edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.pass.word.session.data.model.PasswordItemModel

class ScreenEditComponent constructor(
    componentContext: ComponentContext,
    val passDetailModel: PasswordItemModel,
    private val onGoBack: () -> Unit,
): ComponentContext by componentContext {

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

    fun onEvent(event: ScreenEditEvent) {
        when(event) {
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
        }
    }
}