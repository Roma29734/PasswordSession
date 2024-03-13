package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword

import com.pass.word.session.data.DriverFactory

interface ScreenAddMultiPasswordEvent {
    data class ClickButtonAddNewState(
        val databaseDriverFactory: DriverFactory
    ) : ScreenAddMultiPasswordEvent

    data class UpdateTextTitle(val textTitle: String) : ScreenAddMultiPasswordEvent
    data class UpdateTextEmailORUserName(val textEmailOrUserName: String) :
        ScreenAddMultiPasswordEvent
    data class UpdateTextPassword(val textPassword: String) : ScreenAddMultiPasswordEvent
    data class UpdateTextUrl(val textUrl: String) : ScreenAddMultiPasswordEvent
    data class UpdateTextDescriptions(val textDescriptions: String) : ScreenAddMultiPasswordEvent
}