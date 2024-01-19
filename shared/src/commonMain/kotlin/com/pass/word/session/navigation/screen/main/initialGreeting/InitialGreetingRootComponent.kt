package com.pass.word.session.navigation.screen.main.initialGreeting

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenEnterInitialPassAuth.ScreenEnterInitialPassAuthComponent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenFirstInitial.ScreenFirstInitialComponent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenSecondInitial.ScreenSecondInitialComponent
import kotlinx.serialization.Serializable

class InitialGreetingRootComponent constructor(
    componentContext: ComponentContext,
    private val navigateToAuthScreen: () -> Unit
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
                    navigateToNextScreen = { navigation.pushNew(Configuration.ScreenEnterInitialPassAuth) }
                )
            )

            is Configuration.ScreenEnterInitialPassAuth -> Child.ScreenEnterInitialPassAuth(
                ScreenEnterInitialPassAuthComponent(
                    componentContext = context,
                    clickButtonBack = { navigation.pop() },
                    navigateToNext = { navigation.replaceAll(Configuration.ScreenSecondInitial) }
                )
            )

            is Configuration.ScreenSecondInitial -> Child.ScreenSecondInitial(
                ScreenSecondInitialComponent(
                    componentContext = context,
                    onNavigateToNextScreen = {
                        navigation.replaceAll(Configuration.ScreenImportPassword)
                    }
                )
            )

            is Configuration.ScreenImportPassword -> Child.ScreenImportPassword(
                ScreenImportPasswordComponent(
                    componentContext = context,
                    onNextScreen = {
                        navigateToAuthScreen()
                    }
                )
            )

        }
    }

    sealed class Child {
        data class ScreenFirstInitial(val component: ScreenFirstInitialComponent) : Child()
        data class ScreenEnterInitialPassAuth(val component: ScreenEnterInitialPassAuthComponent) :
            Child()

        data class ScreenSecondInitial(val component: ScreenSecondInitialComponent) : Child()
        data class ScreenImportPassword(val component: ScreenImportPasswordComponent): Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ScreenFirstInitial : Configuration()

        @Serializable
        data object ScreenEnterInitialPassAuth : Configuration()

        @Serializable
        data object ScreenSecondInitial : Configuration()

        @Serializable
        data object ScreenImportPassword: Configuration()
    }
}