package com.pass.word.session.android.screen.initialGreeting.localInitRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.mainApp.enterPassScreen.EnterPassScreen
import com.pass.word.session.android.screen.initialGreeting.ImportPasswordScreen
import com.pass.word.session.android.screen.initialGreeting.SecondInitialScreen
import com.pass.word.session.navigation.screen.initialGreeting.localInitRoot.LocalInitRootComponent

@Composable
fun LocalInitRootScreen(component: LocalInitRootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {

            is LocalInitRootComponent.Child.ScreenEnterInitialPassAuth -> EnterPassScreen(
                component = instance.component
            )

            is LocalInitRootComponent.Child.ScreenSecondInitial -> SecondInitialScreen(
                component = instance.component
            )

            is LocalInitRootComponent.Child.ScreenImportPassword -> ImportPasswordScreen(
                component = instance.component,
            )

        }
    }
}