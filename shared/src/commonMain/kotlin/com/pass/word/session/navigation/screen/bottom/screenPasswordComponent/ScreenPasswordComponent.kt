package com.pass.word.session.navigation.screen.bottom.screenPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.data.root.LocalDataRepository

class ScreenPasswordComponent constructor(
    componentContext: ComponentContext,
    private val onNavigateToDetailComponent: (PasswordItemModel) -> Unit
) : ComponentContext by componentContext {

    private var _passwordListItem: MutableValue<List<PasswordItemModel>> =
        MutableValue(LocalDataRepository().getPasswordItem())
    val passwordListItem: Value<List<PasswordItemModel>> = _passwordListItem

    fun navigateToDetailEvent(model: PasswordItemModel) {
        onNavigateToDetailComponent(model)    }

    fun readBd(databaseDriverFactory: DriverFactory) {
        val database = PersonalDatabase(databaseDriverFactory)

        _passwordListItem.update { database.getAllLaunches() }
    }
}