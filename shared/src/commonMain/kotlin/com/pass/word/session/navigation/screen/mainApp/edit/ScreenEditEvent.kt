package com.pass.word.session.navigation.screen.mainApp.edit

import com.pass.word.session.data.DriverFactory

interface ScreenEditEvent {
    data object ClickButtonBack: ScreenEditEvent
    data class UpdateTextTitle(val textTitle: String) : ScreenEditEvent
    data class UpdateTextEmailORUserName(val textEmailOrUserName: String) :
        ScreenEditEvent
    data class UpdateTextPassword(val textPassword: String) : ScreenEditEvent
    data class UpdateTextUrl(val textUrl: String) : ScreenEditEvent
    data class UpdateTextDescriptions(val textDescriptions: String) : ScreenEditEvent
    data class ClickButtonUpdate(val databaseDriverFactory: DriverFactory): ScreenEditEvent
}