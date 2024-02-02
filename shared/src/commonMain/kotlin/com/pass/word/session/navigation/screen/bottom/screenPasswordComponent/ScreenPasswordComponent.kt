package com.pass.word.session.navigation.screen.bottom.screenPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.data.root.LocalDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ScreenPasswordComponent constructor(
    componentContext: ComponentContext,
    private val onNavigateToDetailComponent: (PasswordItemModel) -> Unit
) : ComponentContext by componentContext {


    private var _stateShowedIcon = MutableValue(false)
    val stateShowedIcon: Value<Boolean> = _stateShowedIcon

    private var _passwordListItem = MutableStateFlow<List<PasswordItemModel>?>(null)
    val passwordListItem get() = _passwordListItem


    fun navigateToDetailEvent(model: PasswordItemModel) {
        onNavigateToDetailComponent(model)
    }

    fun readBd(databaseDriverFactory: DriverFactory) {
        try {
            val database = PersonalDatabase(databaseDriverFactory).getAllPass()
            if(database.isEmpty()) {
                _stateShowedIcon.value = true
                return
            }
            _stateShowedIcon.value = false
            _passwordListItem.update { database }
        } catch (e: Exception) {
            println("Erro pass screen - ${e.message}")
            _stateShowedIcon.value = true
        }
    }
}