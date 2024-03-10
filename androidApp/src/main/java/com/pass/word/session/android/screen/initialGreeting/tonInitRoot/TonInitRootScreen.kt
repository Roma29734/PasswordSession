package com.pass.word.session.android.screen.initialGreeting.tonInitRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.mainApp.enterPassScreen.EnterPassScreen
import com.pass.word.session.android.screen.initialGreeting.checkWalletAddress.CheckWalletAddressScreen
import com.pass.word.session.android.screen.initialGreeting.enterSeedPhrase.EnterSeedPhraseScreen
import com.pass.word.session.navigation.screen.initialGreeting.tonInitRoot.TonInitRootComponent

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
        }
    }
}