package screen.initialGreeting.localInitRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.navigation.screen.initialGreeting.localInitRoot.LocalInitRootComponent
import screen.mainApp.enterPass.EnterPassScreen
import screen.initialGreeting.importPassword.ImportPasswordScreen
import screen.initialGreeting.secondInitial.SecondInitialScreen

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