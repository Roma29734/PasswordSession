package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel

interface ScreenTonPasswordEvent {
    data class ReadBdItem(val databaseDriverFactory: DriverFactory) : ScreenTonPasswordEvent
}

sealed class LoadingTonPassItemState {
    data object InLoading : LoadingTonPassItemState()
    data class InError(val errorMessage: String) : LoadingTonPassItemState()
    data object IsSuccess : LoadingTonPassItemState()
    data object InEmpty : LoadingTonPassItemState()
}

sealed class ResultReadResultFromTonBlock {
    data object InEmpty : ResultReadResultFromTonBlock()
    data class InError(val message: String) : ResultReadResultFromTonBlock()
    data class InSuccess(val itemPass: List<PasswordItemModel>) : ResultReadResultFromTonBlock()
}