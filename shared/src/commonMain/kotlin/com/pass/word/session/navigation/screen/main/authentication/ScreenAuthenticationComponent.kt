package com.pass.word.session.navigation.screen.main.authentication

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class ScreenAuthenticationComponent constructor(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private var _passItem = MutableValue("")
    val passItem: Value<String> = _passItem

    private val listenersSnackBarShow = mutableListOf<(String) -> Unit>()

    fun subscribeListenerSnackBar(listener: (String) -> Unit) {
        listenersSnackBarShow.add(listener)
    }

    fun unsubscribeListenerSnackBar(listener: (String) -> Unit) {
        listenersSnackBarShow.remove(listener)
    }

    private fun showSnackBar(message: String) {
        listenersSnackBarShow.forEach { it.invoke(message) }
    }

    fun event(eventAuth: ScreenAuthStateEvent) {
        when (eventAuth) {
            is ScreenAuthStateEvent.StateBiometric -> {
                if (eventAuth.successState) {
                    showSnackBar("Good")
                } else {
                    println("event state error message")
                    val errorMessage = eventAuth.errorMessage.toString()
                    showSnackBar(errorMessage)
                }
            }
            is ScreenAuthStateEvent.StateUpdatePassItem -> {
                _passItem.value = eventAuth.number
            }
        }
    }
}
