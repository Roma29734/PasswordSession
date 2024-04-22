package com.pass.word.session.android.screen.passKeySettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.passKeySettings.editPassKey.EditPassKeyScreen
import com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.ScreenPassKeySettingsComponent
import com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.primaryAction.PrimaryActionScreen

@Composable
fun PassKeySettingsScreen(component: ScreenPassKeySettingsComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is ScreenPassKeySettingsComponent.Child.ScreenPrimaryKetAction -> PrimaryActionScreen(
                component = instance.component
            )

            is ScreenPassKeySettingsComponent.Child.ScreenEditPassKey -> EditPassKeyScreen(
                component = instance.component
            )
        }
    }

}
