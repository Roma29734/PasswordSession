package com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings

import com.arkivanov.decompose.ComponentContext
import com.pass.word.session.data.PersonalDatabase
import com.pass.word.session.data.TonCashDatabase
import com.pass.word.session.data.clearSettings
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.utilits.StateBasicDialog
import com.pass.word.session.utilits.jsonStringToList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.ton.block.VmCont

class ScreenSeedPhraseSettingsComponent(
    componentContext: ComponentContext,
    private val navToBackScreen: () -> Unit,
    private val navToInitScreen: () -> Unit,
) : ComponentContext by componentContext {


    private val seedPhrase = getParamsString(keyWalletSeed)?.let { jsonStringToList(it) }

    private var _seedPhraseState =  MutableStateFlow<List<String>?>(null)
    val seedPhraseState get() = _seedPhraseState


    private var _stateVisibleDialog =
        MutableStateFlow<StateBasicDialog>(StateBasicDialog.Hide)
    val stateVisibleDialog = _stateVisibleDialog

    init {
        if(seedPhrase != null) {
            _seedPhraseState.update { seedPhrase }
        }
    }

    fun onEvent(event: ScreenSeedPhraseSettingsEvent) {
        when(event) {
            is ScreenSeedPhraseSettingsEvent.NavToBack -> {
                navToBackScreen()
            }

            is ScreenSeedPhraseSettingsEvent.ClickButtonLogOut -> {
                _stateVisibleDialog.update { StateBasicDialog.Show }
            }

            is ScreenSeedPhraseSettingsEvent.HideDialogEvent -> {
                _stateVisibleDialog.update { StateBasicDialog.Hide }
            }

            is ScreenSeedPhraseSettingsEvent.ContinueDialogEvent -> {
                val tonBase = TonCashDatabase(event.databaseDriverFactory)
                val localBase = PersonalDatabase(event.databaseDriverFactory)
                tonBase.clearDatabase()
                localBase.clearDatabase()
                clearSettings()
                _stateVisibleDialog.update { StateBasicDialog.Hide }
                navToInitScreen()
            }

        }
    }
}