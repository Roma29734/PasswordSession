package com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keySecretPassKey
import com.pass.word.session.navigation.screen.multiDivisionRoot.MultiDivisionRootComponent
import com.pass.word.session.utilits.StateBasicDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class ScreenPassKeySettingsComponent(
    componentContext: ComponentContext,
    private val navToBackScreen: () -> Unit,
    private val navToNextScreen: () -> Unit,
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenPrimaryKetAction,
        handleBackButton = true,
        childFactory = ::createChild
    )
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {

            is Configuration.ScreenPrimaryKetAction -> Child.ScreenPrimaryKetAction(
                ScreenPrimaryKeyActionComponent(
                    componentContext = context,
                    navToBackScreen = {
                        navToBackScreen()
                    },
                    navToNextScreen = {
                        navigation.push(Configuration.ScreenEditPassKey(it))
                    }
                )
            )

            is Configuration.ScreenEditPassKey -> Child.ScreenEditPassKey(
                ScreenEditPassKeyComponent(
                    componentContext = context,
                    navToBackScreen = { navigation.pop() },
                    navToNextScreen = {
                        navToNextScreen()
                    },
                    passKeyPhrase = config.passKeyPhrase
                )
            )
        }
    }

    sealed class Child {
        data class ScreenPrimaryKetAction(val component: ScreenPrimaryKeyActionComponent) : Child()
        data class ScreenEditPassKey(val component: ScreenEditPassKeyComponent) : Child()

    }

    @Serializable
    sealed class Configuration {

        @Serializable
        data object ScreenPrimaryKetAction : Configuration()

        @Serializable
        data class ScreenEditPassKey(val passKeyPhrase: String) : Configuration()

    }
}