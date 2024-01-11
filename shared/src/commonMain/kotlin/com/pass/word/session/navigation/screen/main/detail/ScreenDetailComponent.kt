package com.pass.word.session.navigation.screen.main.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel

class ScreenDetailComponent constructor(
    componentContext: ComponentContext,
    val passDetailModel: PasswordItemModel,
    private val onGoBack: () -> Unit,
    private val onGoEditScreen: (passDetailModel: PasswordItemModel) -> Unit
): ComponentContext by componentContext {

    private var _stateOpenAlertDialog = MutableValue(false)
    val stateOpenAlertDialog: Value<Boolean> = _stateOpenAlertDialog

    private fun deleteItem(databaseDriverFactory: DriverFactory) {
        PersonalDatabase(databaseDriverFactory).deleteOneItem(passDetailModel.id)
    }

    fun onEvent(event: ScreenDetailEvent) {
        when(event) {
            is ScreenDetailEvent.ClickButtonBack -> { onGoBack() }
            is ScreenDetailEvent.ShowToast -> {

            }
            is ScreenDetailEvent.ChangeStateOpenedAlertDialog -> {
                _stateOpenAlertDialog.value = event.newState
            }
            is ScreenDetailEvent.DeleteItemPass -> {
                deleteItem(event.databaseDriverFactory)
                onGoBack()
            }
            is ScreenDetailEvent.EditItemPass -> {
                onGoEditScreen(passDetailModel)
            }
        }
    }
}