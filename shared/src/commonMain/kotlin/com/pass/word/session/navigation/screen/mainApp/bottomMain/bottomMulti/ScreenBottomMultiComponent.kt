package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.ScreenAddMultiPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenMultiPasswordComponent
import com.pass.word.session.utilits.StateSelectedType
import kotlinx.serialization.Serializable


class ScreenBottomMultiComponent(
    componentContext: ComponentContext,
    private val onNavigateToDetailComponent: (PasswordItemModel, StateSelectedType) -> Unit,
    private val onNavigateToChangePasswordComponent: () -> Unit,
    private val onNavigateToImportPasswordComponent: () -> Unit,
    private val onNavigateToPhraseSettingsComponent: () -> Unit,
    private val onNavigateToPassKeySettingsComponent: () -> Unit,
    private val onNavToInitScreen: () -> Unit,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenPassword,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private var _selectedItem = MutableValue(0)
    val selectedItem: Value<Int> = _selectedItem

    fun updateSelectedItem(newItem: Int) {
        _selectedItem.value = newItem
    }

    fun openPasswordScreen() {
        navigation.bringToFront(Configuration.ScreenPassword)
    }

    fun openAddPasswordScreen() {
        navigation.bringToFront(Configuration.ScreenAddPassword)
    }

    fun openSettingsScreen() {
        navigation.bringToFront(Configuration.ScreenSettings)
    }


    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.ScreenPassword -> Child.ScreenPassword(
                ScreenMultiPasswordComponent(
                    componentContext = context,
                    onNavigateToDetailComponent = { passwordItemModel, stateSelectedType ->
                        onNavigateToDetailComponent(passwordItemModel, stateSelectedType)
                    }
                )
            )

            is Configuration.ScreenAddPassword -> Child.ScreenAddPassword(
                ScreenAddMultiPasswordComponent(componentContext = context)
            )

            is Configuration.ScreenSettings -> Child.ScreenSettings(
                ScreenSettingsComponent(
                    componentContext = context,
                    stateApp = true,
                    onNavigateToChangePasswordComponent = { onNavigateToChangePasswordComponent() },
                    onNavigateToImportPasswordComponent = { onNavigateToImportPasswordComponent() },
                    onNavigateToPhraseSettingsComponent = {
                        onNavigateToPhraseSettingsComponent()
                    },
                    onNavigateToPassKeySettingsComponent = {
                        onNavigateToPassKeySettingsComponent()
                    },
                    navToInitScreen = {
                        onNavToInitScreen()
                    }
                )
            )

        }
    }

    sealed class Child {
        data class ScreenPassword(val component: ScreenMultiPasswordComponent) : Child()
        data class ScreenAddPassword(val component: ScreenAddMultiPasswordComponent) : Child()
        data class ScreenSettings(val component: ScreenSettingsComponent) : Child()

    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ScreenPassword : Configuration()

        @Serializable
        data object ScreenAddPassword : Configuration()

        @Serializable
        data object ScreenSettings : Configuration()
    }
}