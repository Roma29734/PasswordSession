package com.pass.word.session.android.screen.initialGreeting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.initialGreeting.localInitRoot.LocalInitRootScreen
import com.pass.word.session.android.screen.initialGreeting.tonInitRoot.TonInitRootScreen
import com.pass.word.session.navigation.screen.initialGreeting.InitialGreetingRootComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType.ChoosingTypeScreen
import com.pass.word.session.navigation.screen.initialGreeting.screenFirstInitial.FirstInitialScreen

@Composable
fun InitialGreetingScreen(component: InitialGreetingRootComponent) {
    val childStack by component.childStack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is InitialGreetingRootComponent.Child.ScreenFirstInitial -> FirstInitialScreen(
                component = instance.component
            )

            is InitialGreetingRootComponent.Child.ScreenChoosingType -> ChoosingTypeScreen(component = instance.component)

            is InitialGreetingRootComponent.Child.TonInitRoot -> TonInitRootScreen(component = instance.component)

            is InitialGreetingRootComponent.Child.LocalInitRoot -> LocalInitRootScreen(component = instance.component)
        }
    }
}