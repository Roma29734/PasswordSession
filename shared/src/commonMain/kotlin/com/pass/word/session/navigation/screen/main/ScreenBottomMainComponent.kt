package com.pass.word.session.navigation.screen.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.pass.word.session.navigation.screen.bottom.screenCreateNewComponent.ScreenCreateNewComponent
import com.pass.word.session.navigation.screen.bottom.screenPasswordComponent.ScreenPasswordComponent
import com.pass.word.session.navigation.screen.bottom.screenSettingsComponent.ScreenSettingsComponent
import kotlinx.serialization.Serializable

class ScreenBottomMainComponent constructor(
    componentContext: ComponentContext
): ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenPassword,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun openPasswordScreen() {
        navigation.replaceAll(Configuration.ScreenPassword)
    }
    fun openCreateNewScreen() {
        navigation.replaceAll(Configuration.ScreenCreateNew)
    }

    fun openSettingsScreen() {
        navigation.replaceAll(Configuration.ScreenSettings)
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.ScreenPassword -> Child.ScreenPassword(
                ScreenPasswordComponent(componentContext = context)
            )
            is Configuration.ScreenCreateNew -> Child.ScreenCreateNew(
                ScreenCreateNewComponent(componentContext = context)
            )
            is Configuration.ScreenSettings -> Child.ScreenSettings(
                ScreenSettingsComponent(componentContext = context)
            )
        }
    }

    sealed class Child {
        data class ScreenPassword(val component: ScreenPasswordComponent) : Child()
        data class ScreenCreateNew(val component: ScreenCreateNewComponent) : Child()
        data class ScreenSettings(val component: ScreenSettingsComponent): Child()
    }
    @Serializable
    sealed class Configuration {
        data object ScreenPassword : Configuration()
        data object ScreenCreateNew: Configuration()
        data object ScreenSettings : Configuration()
    }
}