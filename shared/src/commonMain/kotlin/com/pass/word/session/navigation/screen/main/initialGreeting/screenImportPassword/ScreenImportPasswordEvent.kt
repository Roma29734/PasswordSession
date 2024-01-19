package com.pass.word.session.navigation.screen.main.initialGreeting.screenImportPassword

import com.pass.word.session.data.DriverFactory

interface ScreenImportPasswordEvent {
    data object ClickButtonNext : ScreenImportPasswordEvent
    data class ImportData(val data: String,val databaseDriverFactory: DriverFactory): ScreenImportPasswordEvent
    data object ClickBackButton: ScreenImportPasswordEvent
}