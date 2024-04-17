package screen.multiDivisionRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.android.screen.mainApp.editScreen.EditScreen
import com.pass.word.session.navigation.screen.multiDivisionRoot.MultiDivisionRootComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import screen.initialGreeting.importPassword.ImportPasswordScreen
import screen.mainApp.authentication.AuthenticationScreen
import screen.mainApp.bottom.bottomMulti.BottomMultiScreen
import screen.mainApp.changePassword.ChangePasswordRoot
import screen.mainApp.detail.DetailScreen
import screen.mainApp.passKeySettings.PassKeySettingsScreen
import screen.mainApp.seedPhraseSettings.SeedPhraseSettingsScreen

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MultiDivisionRootScreen(component: MultiDivisionRootComponent) {
    val childStack by component.childStack.subscribeAsState()
    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is MultiDivisionRootComponent.Child.ScreenAuthentication -> AuthenticationScreen(
                component = instance.component
            )
            is MultiDivisionRootComponent.Child.ScreenChangePasswordRootComponent -> ChangePasswordRoot(
                component = instance.component
            )
            is MultiDivisionRootComponent.Child.ScreenImportPassword -> ImportPasswordScreen(
                component = instance.component
            )
            is MultiDivisionRootComponent.Child.ScreenBottomMulti -> BottomMultiScreen(component = instance.component)

            is MultiDivisionRootComponent.Child.ScreenDetail -> DetailScreen(component = instance.component)

            is MultiDivisionRootComponent.Child.ScreenEdit -> EditScreen(component = instance.component)

            is MultiDivisionRootComponent.Child.ScreenSeedPhraseSettings -> SeedPhraseSettingsScreen(
                component = instance.component
            )
            is MultiDivisionRootComponent.Child.ScreenPassKeySettings -> PassKeySettingsScreen(
                component = instance.component
            )
        }
    }
}