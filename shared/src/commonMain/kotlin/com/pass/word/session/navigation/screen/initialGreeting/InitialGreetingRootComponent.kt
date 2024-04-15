package com.pass.word.session.navigation.screen.initialGreeting

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.pass.word.session.navigation.screen.initialGreeting.localInitRoot.LocalInitRootComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType.ScreenChoosingTypeComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenFirstInitial.ScreenFirstInitialComponent
import com.pass.word.session.navigation.screen.initialGreeting.tonInitRoot.TonInitRootComponent
import kotlinx.serialization.Serializable

class InitialGreetingRootComponent constructor(
    componentContext: ComponentContext,
    private val navigateToAuthScreen: (multiState: Boolean) -> Unit
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenFirstInitial,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.ScreenFirstInitial -> Child.ScreenFirstInitial(
                ScreenFirstInitialComponent(
                    componentContext = context,
                    navigateToNextScreen = { navigation.pushNew(Configuration.ScreenChoosingType) }
                )
            )

            is Configuration.ScreenChoosingType -> Child.ScreenChoosingType(
                ScreenChoosingTypeComponent(
                    componentContext = context,
                    navToLocalType = {
                        navigation.pushNew(Configuration.LocalInitRoot)
                    },
                    navToTonType = {
                        navigation.pushNew(Configuration.TonInitRoot)
                    },
                    navToBack = {
                        navigation.pop()
                    }
                )
            )

            is Configuration.LocalInitRoot -> Child.LocalInitRoot(
                LocalInitRootComponent(
                    componentContext = context,
                    navigateToBack = { navigation.pop() },
                    navigateToAuthScreen = { navigateToAuthScreen(false) }
                )
            )
            is Configuration.TonInitRoot -> Child.TonInitRoot(
                TonInitRootComponent(
                    componentContext = context,
                    navigateToBack = { navigation.pop() },
                    navigateToAuthScreen = { navigateToAuthScreen(true) }
                )
            )
        }
    }

    sealed class Child {
        data class ScreenFirstInitial(val component: ScreenFirstInitialComponent) : Child()

        data class ScreenChoosingType(val component: ScreenChoosingTypeComponent) : Child()

        data class TonInitRoot(val component: TonInitRootComponent): Child()

        data class LocalInitRoot(val component: LocalInitRootComponent) : Child()

    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ScreenFirstInitial : Configuration()


        @Serializable
        data object ScreenChoosingType : Configuration()

        @Serializable
        data object TonInitRoot: Configuration()

        @Serializable
        data object LocalInitRoot: Configuration()
    }
}