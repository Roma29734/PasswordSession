package com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType

import androidx.compose.runtime.Composable

@Composable
fun ChoosingTypeScreen(component: ScreenChoosingTypeComponent) {
    ScreenChoosingTypeContent(
        handlerNavToBack = {
            component.onEvent(ScreenChoosingTypeEvent.NavToBack)
        },
        handlerNavToTon = {
            component.onEvent(ScreenChoosingTypeEvent.NavToTonVersion)
        },
        handlerNavToLocal = {
            component.onEvent(ScreenChoosingTypeEvent.NavToLocalVersion)
        }
    )
}