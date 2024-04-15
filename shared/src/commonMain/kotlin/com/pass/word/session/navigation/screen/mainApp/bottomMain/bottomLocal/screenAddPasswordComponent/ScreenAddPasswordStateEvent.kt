package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent

import com.pass.word.session.data.DriverFactory

interface ScreenAddPasswordStateEvent {
    data class ClickButtonAddNewState(
        val databaseDriverFactory: DriverFactory
    ) : ScreenAddPasswordStateEvent

    data class UpdateTextTitle(val textTitle: String) : ScreenAddPasswordStateEvent
    data class UpdateTextEmailORUserName(val textEmailOrUserName: String) :
        ScreenAddPasswordStateEvent
    data class UpdateTextPassword(val textPassword: String) : ScreenAddPasswordStateEvent
    data class UpdateTextUrl(val textUrl: String) : ScreenAddPasswordStateEvent
    data class UpdateTextDescriptions(val textDescriptions: String) : ScreenAddPasswordStateEvent
}