package com.pass.word.session.navigation.screen.main.authentication

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyAuthPass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class ScreenAuthenticationComponent constructor(
    componentContext: ComponentContext,
    private val onNavigateToMainScreen: () -> Unit
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

    private fun passCheck(passForCheck: String) {
        // Тут получаем из бд пароль
        GlobalScope.launch {
            val pass = getParamsString(keyAuthPass)
            if(pass == passForCheck) {
                onNavigateToMainScreen()
            } else {
                showSnackBar("pass error")
                _passItem.value = ""
            }
        }
    }

    fun event(eventAuth: ScreenAuthStateEvent) {
        when (eventAuth) {
            is ScreenAuthStateEvent.StateBiometric -> {
                if (eventAuth.successState) {
                    onNavigateToMainScreen()
                } else {
                    println("event state error message")
                    val errorMessage = eventAuth.errorMessage.toString()
                    showSnackBar(errorMessage)
                }
            }
            is ScreenAuthStateEvent.StateUpdatePassItem -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val oldValue = passItem.value
                    val newValue = oldValue + eventAuth.number
                    _passItem.value = newValue
                    if(passItem.value.length == 4){
                        passCheck(passItem.value)
                    }
                }
            }
        }
    }
}