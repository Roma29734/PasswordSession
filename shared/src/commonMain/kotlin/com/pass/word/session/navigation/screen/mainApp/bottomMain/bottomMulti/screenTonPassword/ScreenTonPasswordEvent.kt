package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword

import com.pass.word.session.data.model.PasswordItemModel

interface ScreenTonPasswordEvent  {
}

sealed class LoadingTonPassItemState {
    data object InLoading: LoadingTonPassItemState()
    data class InError (val errorMessage: String): LoadingTonPassItemState()
    data class InSuccess (val itemPass: List<PasswordItemModel>): LoadingTonPassItemState()
    data object InEmpty: LoadingTonPassItemState()
}
