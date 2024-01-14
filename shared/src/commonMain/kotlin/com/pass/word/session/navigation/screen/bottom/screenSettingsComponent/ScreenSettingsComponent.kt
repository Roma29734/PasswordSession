package com.pass.word.session.navigation.screen.bottom.screenSettingsComponent

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.utilits.createAndSaveJsonFile

class ScreenSettingsComponent constructor(
    private val componentContext: ComponentContext
) : ComponentContext by componentContext {


    private val listenerToastPush = mutableListOf<(message: String) -> Unit>()

    fun subscribeListenerToastPush(listener: (message: String) -> Unit) {
        listenerToastPush.add(listener)
    }

    fun unsubscribeListenerToastPush(listener: (message: String) -> Unit) {
        listenerToastPush.remove(listener)
    }

    private fun pluckListenerToastPush(message: String) {
        listenerToastPush.forEach { it.invoke(message) }
    }

    fun clickToButtonDownloadPass(context: Any) {
        createAndSaveJsonFile(context, "examplePass.json")
        pluckListenerToastPush("File Download Success")
    }

}