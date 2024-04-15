package com.pass.word.session.navigation.screen.initialGreeting.tonInitRoot

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.pass.word.session.navigation.screen.initialGreeting.screenCheckWalletAddress.ScreenCheckWalletAddressComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterPassKeySecret.ScreenEnterPassKeySecretComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenEnterSeedPhrase.ScreenEnterSeedPhraseComponent
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.ScreenEnterPassComponent
import kotlinx.serialization.Serializable

class TonInitRootComponent constructor(
    componentContext: ComponentContext,
    private val navigateToAuthScreen: () -> Unit,
    private val navigateToBack: () -> Unit,
) : ComponentContext by componentContext {


    private val navigation = StackNavigation<Configuration>()


    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenEnterSeedPhrase,
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
                    clickButtonBack = { navigation.pop() },
                    navigateToNext = { navigateToAuthScreen() }
                )
            )


            is Configuration.ScreenEnterSeedPhrase -> Child.ScreenEnterSeedPhrase(
                ScreenEnterSeedPhraseComponent(
                    componentContext = context,
                    navToNextScreen = { walletAddress, jsonSeedPhrase ->
                        navigation.pushNew(
                            Configuration.ScreenCheckWalletAddress(
                                walletAddress = walletAddress,
                                jsonSeedPhrase = jsonSeedPhrase
                            )
                        )
                    },
                    navToBackScreen = {
                        navigateToBack()
                    }
                )
            )

            is Configuration.ScreenCheckWalletAddress -> Child.ScreenCheckWalletAddress(
                ScreenCheckWalletAddressComponent(
                    componentContext = context,
                    navToNextScreen = {
                        navigation.pushNew(Configuration.ScreenEnterPassKeySecret)
                    },
                    navToBackScreen = {
                        navigation.pop()
                    },
                    walletAddress = config.walletAddress,
                    jsonSeedPhrase = config.jsonSeedPhrase
                )
            )

            is Configuration.ScreenEnterPassKeySecret -> Child.ScreenEnterPassKeySecret(
                ScreenEnterPassKeySecretComponent(
                    componentContext = context,
                    navToNextScreen = {
                        navigation.pushNew(Configuration.ScreenEnterInitialPassAuth)
                    },
                    navToBackScreen = {
                        navigation.pop()
                    }
                )
            )

        }
    }


    sealed class Child {
        data class ScreenEnterInitialPassAuth(val component: ScreenEnterPassComponent) :
            Child()

        data class ScreenEnterSeedPhrase(val component: ScreenEnterSeedPhraseComponent) : Child()

        data class ScreenCheckWalletAddress(val component: ScreenCheckWalletAddressComponent) :
            Child()

        data class ScreenEnterPassKeySecret(val component: ScreenEnterPassKeySecretComponent): Child()
    }

    @Serializable
    sealed class Configuration {

        @Serializable
        data object ScreenEnterInitialPassAuth : Configuration()

        @Serializable
        data object ScreenEnterSeedPhrase : Configuration()

        @Serializable
        data class ScreenCheckWalletAddress(
            val walletAddress: String,
            val jsonSeedPhrase: String
        ) : Configuration()

        @Serializable
        data object ScreenEnterPassKeySecret: Configuration()
    }
}