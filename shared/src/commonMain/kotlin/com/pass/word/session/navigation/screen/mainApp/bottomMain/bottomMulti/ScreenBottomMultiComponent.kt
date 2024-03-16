package com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.ScreenBottomLocalComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenAddPasswordComponent.ScreenAddPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenPasswordComponent.ScreenPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenSettingsComponent.ScreenSettingsComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenAddMultiPassword.ScreenAddMultiPasswordComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.screenTonPassword.ScreenTonPasswordComponent
import kotlinx.serialization.Serializable


class ScreenBottomMultiComponent(
    componentContext: ComponentContext,
    private val onNavigateToDetailComponent: (PasswordItemModel) -> Unit,
    private val onNavigateToChangePasswordComponent: () -> Unit,
    private val onNavigateToImportPasswordComponent: () -> Unit
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
                ScreenTonPasswordComponent(
                    componentContext = context,
                )
            )

            is Configuration.ScreenAddPassword -> Child.ScreenAddPassword(
                ScreenAddMultiPasswordComponent(componentContext = context)
            )

            is Configuration.ScreenSettings -> Child.ScreenSettings(
                ScreenSettingsComponent(
                    componentContext = context,
                    onNavigateToChangePasswordComponent = { onNavigateToChangePasswordComponent() },
                    onNavigateToImportPasswordComponent = { onNavigateToImportPasswordComponent() }
                )
            )

        }
    }

    sealed class Child {
        data class ScreenPassword(val component: ScreenTonPasswordComponent) : Child()
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