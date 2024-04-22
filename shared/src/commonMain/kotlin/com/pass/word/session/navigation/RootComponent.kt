package com.pass.word.session.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keyAuthPass
import com.pass.word.session.data.keySecretPassKey
import com.pass.word.session.data.keyWalletSeed
import com.pass.word.session.navigation.screen.localDivisionRoot.LocalDivisionRootComponent
import com.pass.word.session.navigation.screen.initialGreeting.InitialGreetingRootComponent
import com.pass.word.session.navigation.screen.multiDivisionRoot.MultiDivisionRootComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.Serializable

class RootComponent constructor(
    componentContext: ComponentContext
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = checkStateAppLoading(),
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun checkStateAppLoading(): Configuration {

        getParamsString(keyAuthPass) ?: return Configuration.ScreenInitialGreeting
        if (getParamsString(keyWalletSeed) == null) {
            return Configuration.LocalDivisionRoot
        }
        getParamsString(keySecretPassKey) ?: return Configuration.ScreenInitialGreeting
        return Configuration.MultiDivisionRoot
    }

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {


            is Configuration.ScreenInitialGreeting -> Child.ScreenInitialGreeting(
                InitialGreetingRootComponent(
                    componentContext = context,
                    navigateToAuthScreen = {
                        if (it) {
                            navigation.replaceCurrent(Configuration.MultiDivisionRoot)
                        } else {
                            navigation.replaceCurrent(Configuration.LocalDivisionRoot)
                        }
                    }
                )
            )

            is Configuration.LocalDivisionRoot -> Child.LocalDivisionRoot(
                LocalDivisionRootComponent(componentContext = context)
            )

            is Configuration.MultiDivisionRoot -> Child.MultiDivisionRoot(
                MultiDivisionRootComponent(componentContext = context, navToInitScreen = {
                    navigation.replaceAll(Configuration.ScreenInitialGreeting)
                })
            )
        }
    }

    sealed class Child {

        data class ScreenInitialGreeting(val component: InitialGreetingRootComponent) : Child()

        data class LocalDivisionRoot(val component: LocalDivisionRootComponent) : Child()

        data class MultiDivisionRoot(val component: MultiDivisionRootComponent) : Child()
    }

    @Serializable
    sealed class Configuration {

        @Serializable
        data object ScreenInitialGreeting : Configuration()

        @Serializable
        data object LocalDivisionRoot : Configuration()

        @Serializable
        data object MultiDivisionRoot : Configuration()
    }
}