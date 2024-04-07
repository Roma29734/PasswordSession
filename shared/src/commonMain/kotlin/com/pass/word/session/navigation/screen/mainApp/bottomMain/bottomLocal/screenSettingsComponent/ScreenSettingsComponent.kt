package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.utilits.convertListToJsonObject
import com.pass.word.session.utilits.createAndSaveJsonFile

class ScreenSettingsComponent constructor(
    private val componentContext: ComponentContext,
    private val onNavigateToChangePasswordComponent: () -> Unit,
    private val onNavigateToImportPasswordComponent: () -> Unit,
    private val onNavigateToPhraseSettingsComponent: () -> Unit,
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


    fun onEvent(event: ScreenSettingsStateEvent) {
        when(event) {
            is ScreenSettingsStateEvent.ClickToButtonDownloadPass -> {
                val result = convertListToJsonObject(PersonalDatabase(event.databaseDriverFactory).getAllPass())
                println("jsonResult - $result")
                createAndSaveJsonFile(event.context, "examplePass.json", result)
                pluckListenerToastPush("File Download Success")
            }
            is ScreenSettingsStateEvent.OnNavigateToChangePasswordComponent -> {
                onNavigateToChangePasswordComponent()
            }
            is ScreenSettingsStateEvent.OnNavigateToImportPassword -> {
                onNavigateToImportPasswordComponent()
            }
            is ScreenSettingsStateEvent.OnNavigateToSeedPhraseSettings -> {
                onNavigateToPhraseSettingsComponent()
            }
        }
    }

}