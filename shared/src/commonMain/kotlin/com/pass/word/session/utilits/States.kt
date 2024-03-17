package com.pass.word.session.utilits

import com.pass.word.session.data.model.PasswordItemModel

sealed class ResponseStatus() {
    data object Success: ResponseStatus()
    data class Error (val errorMessage: String): ResponseStatus()
}

enum class StateSelectedType {
    TonStorage, LocalStorage
}


sealed class StatePassItemDisplay {
    data class VisibleItem(val passItem: List<PasswordItemModel>?): StatePassItemDisplay()
    data object VisibleEmpty: StatePassItemDisplay()
    data object VisibleNothing: StatePassItemDisplay()
}

sealed class StateBasicLoadingDialog {
    data object Hide: StateBasicLoadingDialog()
    data class Error(val message: String): StateBasicLoadingDialog()
    data object ShowLoading: StateBasicLoadingDialog()
}