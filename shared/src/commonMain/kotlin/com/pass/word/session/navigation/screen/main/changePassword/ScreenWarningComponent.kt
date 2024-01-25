package com.pass.word.session.navigation.screen.main.changePassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.subscribe

class ScreenWarningComponent constructor(
    componentContext: ComponentContext,
    private val onBackNavigation: () -> Unit,
    private val onNextScreenNavigation: () -> Unit,
): ComponentContext by componentContext {

    fun onBackNavigate() {
        onBackNavigation()
    }

    fun onNextNavigate() {
        onNextScreenNavigation()
    }

    init {
        lifecycle.subscribe(
            object : Lifecycle.Callbacks {
                override fun onCreate() {
                    /* Component created */
                }

                // onStart, onResume, onPause, onStop, onDestroy
            }
        )

        lifecycle.subscribe(
            onCreate = { /* Component created */ },
            // onStart, onResume, onPause, onStop, onDestroy
        )

        lifecycle.doOnCreate { /* Component created */ }
        // doOnStart, doOnResume, doOnPause, doOnStop, doOnDestroy
    }
}