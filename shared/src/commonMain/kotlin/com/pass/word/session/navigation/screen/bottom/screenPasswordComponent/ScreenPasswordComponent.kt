package com.pass.word.session.navigation.screen.bottom.screenPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.navigation.data.model.PasswordItemModel
import com.pass.word.session.navigation.data.root.LocalDataRepository

class ScreenPasswordComponent constructor(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    private var _passwordListItem = MutableValue(LocalDataRepository().getPasswordItem())
    val passwordListItem: Value<List<PasswordItemModel>> = _passwordListItem

}