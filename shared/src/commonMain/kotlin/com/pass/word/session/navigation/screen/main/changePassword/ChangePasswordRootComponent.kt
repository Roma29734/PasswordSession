package com.pass.word.session.navigation.screen.main.changePassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.pass.word.session.navigation.screen.main.screenEnterInitialPassAuth.ScreenEnterPassComponent
import kotlinx.serialization.Serializable

class ChangePasswordRootComponent constructor(
    componentContext: ComponentContext,
    private val onBackNavigation: () -> Unit,
    private val onNextNavigation: () -> Unit,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenWarning,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is Configuration.ScreenWarning -> Child.ScreenWarning(
                ScreenWarningComponent(
                    componentContext = context,
                    onBackNavigation = { onBackNavigation() },
                    onNextScreenNavigation = { navigation.pushNew(Configuration.ScreenChangePassword) }
                )
            )

            is Configuration.ScreenChangePassword -> Child.ScreenChangePassword(
                ScreenEnterPassComponent(
                    componentContext = context,
                    clickButtonBack = { navigation.pop() },
                    navigateToNext = {
                        onNextNavigation()
                    }
                )
            )
        }
    }


    sealed class Child {
        data class ScreenWarning(val component: ScreenWarningComponent) : Child()
        data class ScreenChangePassword(val component: ScreenEnterPassComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ScreenWarning : Configuration()

        @Serializable
        data object ScreenChangePassword : Configuration()
    }
}