package com.pass.word.session.utilits

import com.pass.word.session.data.model.PasswordItemModel

sealed class ResponseStatus() {
    data object Success : ResponseStatus()
    data class Error(val errorMessage: String) : ResponseStatus()
}

enum class StateSelectedType {
    TonStorage, LocalStorage
}


sealed class StatePassItemDisplay {
    data class VisibleItem(val passItem: List<PasswordItemModel>?) : StatePassItemDisplay()
    data class VisibleMessage(val message: String) : StatePassItemDisplay()
    data object VisibleNothing : StatePassItemDisplay()
}

sealed class StateStatusBar {
    data class Show(val message: String) : StateStatusBar()
    data object Hide : StateStatusBar()
}

sealed class StateBasicDialog {
    data object Hide : StateBasicDialog()
    data class Error(val message: String) : StateBasicDialog()
    data object Show : StateBasicDialog()
}

sealed class ResultReadResultFromTonBlock {
    data object InEmpty : ResultReadResultFromTonBlock()
    data class InError(val message: String, val errorCode: ResponseCodState) :
        ResultReadResultFromTonBlock()

    data class InSuccess(val itemPass: List<PasswordItemModel>) : ResultReadResultFromTonBlock()
}

// Базовый стейт результата работы функции
sealed class StateBasicResult<out T> {
    data class InError(val message: String, val errorCode: ResponseCodState) :
        StateBasicResult<Nothing>()

    data class InSuccess<T>(val item: T) : StateBasicResult<T>()
}



