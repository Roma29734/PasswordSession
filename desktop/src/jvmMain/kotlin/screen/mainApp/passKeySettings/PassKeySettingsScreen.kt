package screen.mainApp.passKeySettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import screen.mainApp.passKeySettings.editPassKey.EditPassKeyScreen
import screen.mainApp.passKeySettings.primaryAction.PrimaryActionScreen
import com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.ScreenPassKeySettingsComponent

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
