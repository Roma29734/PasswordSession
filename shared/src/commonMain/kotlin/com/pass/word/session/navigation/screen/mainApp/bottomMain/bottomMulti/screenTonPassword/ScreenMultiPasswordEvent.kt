package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.utilits.StateSelectedType

interface ScreenMultiPasswordEvent {
    data class ReadBdItem(val databaseDriverFactory: DriverFactory) : ScreenMultiPasswordEvent
    data class UpdateSelectedType(val databaseDriverFactory: DriverFactory, val newType: StateSelectedType): ScreenMultiPasswordEvent
    data class ClickToItem(val model: PasswordItemModel): ScreenMultiPasswordEvent
    data class ReadCashPass(val databaseDriverFactory: DriverFactory): ScreenMultiPasswordEvent
    data object HideDialog: ScreenMultiPasswordEvent
}

