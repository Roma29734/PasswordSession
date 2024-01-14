package com.pass.word.session.navigation.screen.main.changePassword

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable

class ChangePasswordRootComponent constructor(
    componentContext: ComponentContext,
    private val onBackNavigation: () -> Unit
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
                ScreenChangePasswordComponent(componentContext = context)
            )
        }
    }


    sealed class Child {
        data class ScreenWarning(val component: ScreenWarningComponent) : Child()
        data class ScreenChangePassword(val component: ScreenChangePasswordComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ScreenWarning : Configuration()

        @Serializable
        data object ScreenChangePassword : Configuration()
    }
}