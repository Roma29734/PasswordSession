package com.pass.word.session.navigation.screen.main.initialGreeting.screenImportPassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.subscribe
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.model.PasswordListContainer
import kotlinx.serialization.json.Json

class ScreenImportPasswordComponent constructor(
    componentContext: ComponentContext,
    private val onNextScreen: () -> Unit,
) : ComponentContext by componentContext {

    private var _stateShowCompleteView = MutableValue(false)
    val stateShowCompleteView: Value<Boolean> = _stateShowCompleteView

    private val listenerEvent = mutableListOf<(message: String) -> Unit>()

    fun subscribeListenerEvent(listener: (message: String) -> Unit) {
        listenerEvent.add(listener)
    }

    fun unsubscribeListenerEvent(listener: (message: String) -> Unit) {
        listenerEvent.remove(listener)
    }

    private fun pluckListenerEvent(message: String) {
        listenerEvent.forEach { it.invoke(message) }
    }

    fun event(event: ScreenImportPasswordEvent) {
        when (event) {
            is ScreenImportPasswordEvent.ClickButtonNext -> {
                onNextScreen()
            }

            is ScreenImportPasswordEvent.ImportData -> {
                try {
                    val newJson = Json.decodeFromString<PasswordListContainer>(event.data)
                    println("json $newJson")
                    PersonalDatabase(event.databaseDriverFactory).createPass(newJson.passwordList)
                    _stateShowCompleteView.value = true
                } catch (e: Exception) {
                    pluckListenerEvent("The wrong file is selected")
                    println("error - $e")
                }
            }
        }
    }

    init {
        lifecycle.run {
            subscribe(
                object : Lifecycle.Callbacks {
                    override fun onCreate() {
                        /* Component created */

                    }

                    // onStart, onResume, onPause, onStop, onDestroy
                }
            )

            subscribe(
                onCreate = { /* Component created */ },
                // onStart, onResume, onPause, onStop, onDestroy
            )
        }

        lifecycle.doOnCreate { /* Component created */ }
        // doOnStart, doOnResume, doOnPause, doOnStop, doOnDestroy
    }

}