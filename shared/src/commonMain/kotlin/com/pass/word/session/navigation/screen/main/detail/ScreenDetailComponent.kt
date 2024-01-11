package com.pass.word.session.navigation.screen.main.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.model.PasswordItemModel

class ScreenDetailComponent constructor(
    componentContext: ComponentContext,
    val passDetailModel: PasswordItemModel,
    private val onGoBack: () -> Unit,
): ComponentContext by componentContext {

    private var _stateOpenAlertDialog = MutableValue(false)
    val stateOpenAlertDialog: Value<Boolean> = _stateOpenAlertDialog

    fun onEvent(event: ScreenDetailEvent) {
        when(event) {
            is ScreenDetailEvent.ClickButtonBack -> { onGoBack() }
            is ScreenDetailEvent.ShowToast -> {

            }
            is ScreenDetailEvent.ChangeStateOpenedAlertDialog -> {
                _stateOpenAlertDialog.value = event.newState
            }
            is ScreenDetailEvent.DeleteItemPass -> {
                onGoBack()
            }
        }
    }
}