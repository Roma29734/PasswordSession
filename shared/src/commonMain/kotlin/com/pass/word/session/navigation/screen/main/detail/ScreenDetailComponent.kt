package com.pass.word.session.navigation.screen.main.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenDetailComponent constructor(
    componentContext: ComponentContext,
    val passDetailModel: PasswordItemModel,
    private val onGoBack: () -> Unit,
    private val onGoEditScreen: (passDetailModel: PasswordItemModel) -> Unit
): ComponentContext by componentContext {

    private var _stateOpenAlertDialog = MutableValue(false)
    val stateOpenAlertDialog: Value<Boolean> = _stateOpenAlertDialog

    private var _passwordItem: MutableValue<PasswordItemModel> =
        MutableValue(passDetailModel)
    val passwordItem: Value<PasswordItemModel> = _passwordItem

    fun getOneItem(databaseDriverFactory: DriverFactory) {
        val result = PersonalDatabase(databaseDriverFactory).getOneItemPass(passDetailModel.id)
        if(result != null) {
            _passwordItem.value = result
        }
    }

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
                GlobalScope.launch {
                    deleteItem(event.databaseDriverFactory)
                    onGoBack()
                }
            }
            is ScreenDetailEvent.EditItemPass -> {
                onGoEditScreen(passDetailModel)
            }
        }
    }
}