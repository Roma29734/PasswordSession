package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.TonCashDatabase
import com.pass.word.session.data.clearSettings
import com.pass.word.session.utilits.EventDispatcher
import com.pass.word.session.utilits.StateBasicDialog
import com.pass.word.session.utilits.convertListToJsonObject
import com.pass.word.session.utilits.createAndSaveJsonFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ScreenSettingsComponent constructor(
    private val componentContext: ComponentContext,
    stateApp: Boolean,
    private val onNavigateToChangePasswordComponent: () -> Unit,
    private val onNavigateToImportPasswordComponent: () -> Unit,
    private val onNavigateToPhraseSettingsComponent: () -> Unit,
    private val onNavigateToPassKeySettingsComponent: () -> Unit,
    private val navToInitScreen: () -> Unit,
) : ComponentContext by componentContext {

    val showSnackBarDispatcher = EventDispatcher<String>()

    private var _itemSettingsList: MutableStateFlow<List<ItemSettings>> = MutableStateFlow(listOf())
    val itemSettingsList get() = _itemSettingsList


    private var _stateVisibleDialog =
        MutableStateFlow<StateBasicDialog>(StateBasicDialog.Hide)
    val stateVisibleDialog = _stateVisibleDialog

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
        itemList.add(ItemSettings.LogOut)
        _itemSettingsList.update { itemList }
    }


    fun onEvent(event: ScreenSettingsStateEvent) {
        when(event) {
            is ScreenSettingsStateEvent.ClickToButtonDownloadPass -> {
                val result = convertListToJsonObject(PersonalDatabase(event.databaseDriverFactory).getAllPass())
                println("jsonResult - $result")
                event.context?.let { createAndSaveJsonFile(it, "examplePass.json", result) }
                showSnackBarDispatcher.dispatch("File Download Success")
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

            is ScreenSettingsStateEvent.OnClickLogOut -> {
                _stateVisibleDialog.update { StateBasicDialog.Show }
            }
            is ScreenSettingsStateEvent.OnClickInDialogButton -> {
                _stateVisibleDialog.update { StateBasicDialog.Hide }
                if(event.clickButtonContinue) {
                    val tonBase = TonCashDatabase(event.databaseDriverFactory)
                    val localBase = PersonalDatabase(event.databaseDriverFactory)
                    tonBase.clearDatabase()
                    localBase.clearDatabase()
                    clearSettings()
                    navToInitScreen()
                }
            }
        }
    }
}