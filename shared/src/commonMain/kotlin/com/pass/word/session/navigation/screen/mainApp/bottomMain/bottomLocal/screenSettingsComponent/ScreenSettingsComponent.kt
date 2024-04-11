package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.utilits.convertListToJsonObject
import com.pass.word.session.utilits.createAndSaveJsonFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ScreenSettingsComponent constructor(
    private val componentContext: ComponentContext,
    private val stateApp: Boolean,
    private val onNavigateToChangePasswordComponent: () -> Unit,
    private val onNavigateToImportPasswordComponent: () -> Unit,
    private val onNavigateToPhraseSettingsComponent: () -> Unit,
    private val onNavigateToPassKeySettingsComponent: () -> Unit
) : ComponentContext by componentContext {


    private val listenerToastPush = mutableListOf<(message: String) -> Unit>()

    private var _itemSettingsList: MutableStateFlow<List<ItemSettings>> = MutableStateFlow(listOf())
    val itemSettingsList get() = _itemSettingsList

    init {
        val itemList = mutableListOf<ItemSettings>()
        itemList.add(ItemSettings.ImportPassword)
        itemList.add(ItemSettings.ChangePassword)
        if(stateApp) {
            itemList.add(ItemSettings.SeedPhraseSettings)
            itemList.add(ItemSettings.PassKeySettings)
        }
        itemList.add(ItemSettings.GitHub)
        itemList.add(ItemSettings.Telegram)
        _itemSettingsList.update { itemList }
    }

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
            is ScreenSettingsStateEvent.OnNavigateToPassKeySettingsComponent -> {
                onNavigateToPassKeySettingsComponent()
            }
        }
    }
}