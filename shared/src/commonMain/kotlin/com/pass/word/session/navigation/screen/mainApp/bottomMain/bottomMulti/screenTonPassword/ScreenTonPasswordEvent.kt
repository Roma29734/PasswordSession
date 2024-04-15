package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.utilits.StateSelectedType

interface ScreenTonPasswordEvent {
    data class ReadBdItem(val databaseDriverFactory: DriverFactory) : ScreenTonPasswordEvent
    data class UpdateSelectedType(val databaseDriverFactory: DriverFactory, val newType: StateSelectedType): ScreenTonPasswordEvent
    data class ClickToItem(val model: PasswordItemModel): ScreenTonPasswordEvent
    data class ReadCashPass(val databaseDriverFactory: DriverFactory): ScreenTonPasswordEvent
    data object HideDialog: ScreenTonPasswordEvent
}

