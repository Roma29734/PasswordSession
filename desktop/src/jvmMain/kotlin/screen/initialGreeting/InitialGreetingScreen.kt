package screen.initialGreeting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.navigation.screen.initialGreeting.InitialGreetingRootComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType.ChoosingTypeScreen
import screen.initialGreeting.firstInitial.FirstInitialScreen
import screen.initialGreeting.localInitRoot.LocalInitRootScreen
import screen.initialGreeting.tonInitRoot.TonInitRootScreen

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