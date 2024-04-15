package com.pass.word.session.navigation

sealed class RootStateApp {
    data object NotActive : RootStateApp()
    data object LocalStorageState : RootStateApp()
    data object MultiStorageState : RootStateApp()
}