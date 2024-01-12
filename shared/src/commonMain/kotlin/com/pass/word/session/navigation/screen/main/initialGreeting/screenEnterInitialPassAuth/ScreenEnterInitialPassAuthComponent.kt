package com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterInitialPassAuth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.keyAuthPass
import com.pass.word.session.data.putToParams

class ScreenEnterInitialPassAuthComponent constructor(
    componentContext: ComponentContext,
    private val clickButtonBack: () -> Unit,
    private val navigateToNext: () -> Unit
) : ComponentContext by componentContext {
    private var _passItem = MutableValue("")
    val passItem: Value<String> = _passItem

    fun onEvent(event: ScreenEnterInitialPassAuthEvent) {
        when (event) {
            is ScreenEnterInitialPassAuthEvent.ClickButtonBack -> {
                clickButtonBack()
            }

            is ScreenEnterInitialPassAuthEvent.StateUpdatePassItem -> {
                val oldValue = passItem.value
                val newValue = oldValue + event.newCod
                _passItem.value = newValue
                if (passItem.value.length == 4) {
                    passItem.value.putToParams(keyAuthPass)
                    navigateToNext()
                }
            }
        }
    }
}