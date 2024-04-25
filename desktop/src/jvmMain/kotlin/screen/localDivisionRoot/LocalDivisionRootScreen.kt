package screen.localDivisionRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import screen.mainApp.edit.EditScreen

import com.pass.word.session.navigation.screen.localDivisionRoot.LocalDivisionRootComponent
import screen.initialGreeting.importPassword.ImportPasswordScreen
import screen.mainApp.authentication.AuthenticationScreen
import screen.mainApp.bottom.bottomLocal.BottomLocalScreen
import screen.mainApp.changePassword.ChangePasswordRoot
import screen.mainApp.detail.DetailScreen

@Composable
fun LocalDivisionRootScreen(component: LocalDivisionRootComponent) {

    val childStack by component.childStack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is LocalDivisionRootComponent.Child.ScreenAuthentication -> AuthenticationScreen(
                component = instance.component
            )

            is LocalDivisionRootComponent.Child.ScreenBottomLocal -> BottomLocalScreen(component = instance.component)

            is LocalDivisionRootComponent.Child.ScreenDetail -> DetailScreen(component = instance.component)

            is LocalDivisionRootComponent.Child.ScreenChangePasswordRootComponent -> ChangePasswordRoot(
                component = instance.component
            )

            is LocalDivisionRootComponent.Child.ScreenImportPassword -> ImportPasswordScreen(
                component = instance.component
            )

            is LocalDivisionRootComponent.Child.ScreenEdit -> EditScreen(component = instance.component)

        }
    }

}