package com.pass.word.session.navigation.screen.mainApp.authentication

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.subscribe
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyAuthPass
import com.pass.word.session.utilits.vibrationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
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


    private var pass: String? = null
    var clickState = false
    fun unsubscribeListenerSnackBar(listener: (String) -> Unit) {
        listenersSnackBarShow.remove(listener)
    }

    private fun showSnackBar(message: String) {
        listenersSnackBarShow.forEach { it.invoke(message) }
    }

    fun event(eventAuth: ScreenAuthStateEvent) {
        when (eventAuth) {
            is ScreenAuthStateEvent.ReactionToFollowingBiometrics -> {
                println("pass $pass")
                if (eventAuth.successState) {
                    _passItem.value = pass.orEmpty()
                    onNavigateToMainScreen()
                    vibrationResponse(50, eventAuth.context)
                } else {
                    println("event state error message")
                    val errorMessage = eventAuth.errorMessage.toString()
                    showSnackBar(errorMessage)
                    vibrationResponse(400, eventAuth.context)
                }
            }

            is ScreenAuthStateEvent.ReactionToFirstBiometrics -> {
                if (eventAuth.successState) {
                    _passItem.value = pass.orEmpty()
                    onNavigateToMainScreen()
                    vibrationResponse(50, eventAuth.context)
                }
            }

            is ScreenAuthStateEvent.StateUpdatePassItem -> {
                vibrationResponse(20, eventAuth.context)
                CoroutineScope(Dispatchers.Main).launch {
                    val oldValue = passItem.value
                    if(oldValue.length != 4) {
                        val newValue = oldValue + eventAuth.number
                        _passItem.value = newValue
                    }
                    if (passItem.value.length == 4) {
                        println("clickState afterClick - $clickState")
                        if (!clickState) {
                            clickState = true
                            GlobalScope.launch {
                                delay(300)
                                if (pass == passItem.value) {
                                    onNavigateToMainScreen()
                                } else {
                                    showSnackBar("pass error")
                                    _passItem.value = ""
                                    vibrationResponse(400, eventAuth.context)
                                }
                            }
                        } else {
                            if (pass == passItem.value) {
                                onNavigateToMainScreen()
                            } else {
                                _passItem.value = ""
                                vibrationResponse(400, eventAuth.context)
                            }
                        }
                    }
                }
            }
        }
    }

    init {
        lifecycle.run {
            subscribe(
                onResume = {
                    pass = getParamsString(keyAuthPass)
                    println("pass $pass")
                }
            )
        }
    }
}