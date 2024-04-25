package screen.initialGreeting.tonInitRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.navigation.screen.initialGreeting.screenCheckWalletAddress.CheckWalletAddressScreen
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret.EnterPassKeySecretScreen
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.EnterSeedPhraseScreen
import com.pass.word.session.navigation.screen.initialGreeting.tonInitRoot.TonInitRootComponent
import screen.mainApp.enterPass.EnterPassScreen

@Composable
fun TonInitRootScreen(component: TonInitRootComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {

            is TonInitRootComponent.Child.ScreenEnterInitialPassAuth -> EnterPassScreen(
                component = instance.component
            )

            is TonInitRootComponent.Child.ScreenEnterSeedPhrase -> EnterSeedPhraseScreen(
                component = instance.component
            )

            is TonInitRootComponent.Child.ScreenCheckWalletAddress -> CheckWalletAddressScreen(
                component = instance.component
            )

            is TonInitRootComponent.Child.ScreenEnterPassKeySecret -> EnterPassKeySecretScreen(
                component = instance.component
            )
        }
    }

}