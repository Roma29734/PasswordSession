package com.pass.word.session.navigation.screen.main.edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.utilits.getThisLocalTime
import com.pass.word.session.utilits.onCheckValidation

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

    private val listenersPassCreate = mutableListOf<(message: String) -> Unit>()

    fun subscribeListenerPush(listener: (message: String) -> Unit) {
        listenersPassCreate.add(listener)
    }

    fun unsubscribeListenerPush(listener: (message: String) -> Unit) {
        listenersPassCreate.remove(listener)
    }

    private fun pluckListenerPush(message: String) {
        listenersPassCreate.forEach { it.invoke(message) }
    }

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
            is ScreenEditEvent.ClickButtonUpdate -> {
                if(textTitle.value.isEmpty() || textEmailOrUserName.value.isEmpty() || textPassword.value.isEmpty()) {
                    pluckListenerPush("The first three fields must be filled in")
                } else {
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
                    PersonalDatabase(event.databaseDriverFactory).updatePassItem(model)
                    onGoBack()
                }
            }
        }
    }
}