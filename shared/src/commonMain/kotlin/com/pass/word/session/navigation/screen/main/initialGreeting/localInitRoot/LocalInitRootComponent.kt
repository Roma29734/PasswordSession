package com.pass.word.session.navigation.screen.main.initialGreeting.localInitRoot

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.pass.word.session.navigation.screen.main.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.main.initialGreeting.screenSecondInitial.ScreenSecondInitialComponent
import com.pass.word.session.navigation.screen.main.screenEnterPass.ScreenEnterPassComponent
import kotlinx.serialization.Serializable

class LocalInitRootComponent constructor(
    componentContext: ComponentContext,
    private val navigateToAuthScreen: () -> Unit,
    private val navigateToBack: () -> Unit,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()


    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenEnterInitialPassAuth,
        handleBackButton = true,
        childFactory = ::createChild
    )


    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {

            is Configuration.ScreenEnterInitialPassAuth -> Child.ScreenEnterInitialPassAuth(
                ScreenEnterPassComponent(
                    componentContext = context,
                    clickButtonBack = { navigateToBack() },
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
                    },
                    onBackHandler = null
                )
            )

        }
    }


    sealed class Child {
        data class ScreenEnterInitialPassAuth(val component: ScreenEnterPassComponent) :
            Child()

        data class ScreenSecondInitial(val component: ScreenSecondInitialComponent) : Child()
        data class ScreenImportPassword(val component: ScreenImportPasswordComponent) : Child()
    }

    @Serializable
    sealed class Configuration {

        @Serializable
        data object ScreenEnterInitialPassAuth : Configuration()

        @Serializable
        data object ScreenSecondInitial : Configuration()

        @Serializable
        data object ScreenImportPassword : Configuration()

    }
}