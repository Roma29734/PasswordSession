package com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent

import com.pass.word.session.data.DriverFactory

interface ScreenAddPasswordStateEvent {
    data class ClickButtonAddNewState(
        val title: String,
        val email: String,
        val pass: String,
        val url: String?,
        val descriptions: String?,
        val databaseDriverFactory: DriverFactory
    ) : ScreenAddPasswordStateEvent
}