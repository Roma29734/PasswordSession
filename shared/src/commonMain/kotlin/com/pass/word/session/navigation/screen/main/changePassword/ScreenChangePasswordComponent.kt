package com.pass.word.session.navigation.screen.main.changePassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.keyAuthPass
import com.pass.word.session.data.putToParams

class ScreenChangePasswordComponent constructor(
    componentContext: ComponentContext,
    private val onBackNavigation: () -> Unit,
    private val onNextScreenNavigation: () -> Unit,
): ComponentContext by componentContext {

    private var _passItem = MutableValue("")
    val passItem: Value<String> = _passItem


    fun onEvent(event: ChangePasswordEvent) {
        when(event) {
            is ChangePasswordEvent.ClickButtonBack -> {
                onBackNavigation()
            }
            is ChangePasswordEvent.StateUpdatePassItem -> {
                val oldValue = passItem.value
                val newValue = oldValue + event.newCod
                _passItem.value = newValue
                if (passItem.value.length == 4) {
                    passItem.value.putToParams(keyAuthPass)
                    onNextScreenNavigation()
                }
            }
        }
    }

}