package com.pass.word.session.navigation.screen.main.bottomMain


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.bottom.screenAddPasswordComponent.ScreenAddPasswordComponent
import com.pass.word.session.navigation.screen.bottom.screenPasswordComponent.ScreenPasswordComponent
import com.pass.word.session.navigation.screen.bottom.screenSettingsComponent.ScreenSettingsComponent
import kotlinx.serialization.Serializable

class ScreenBottomMainComponent constructor(
    componentContext: ComponentContext,
    private val onNavigateToDetailComponent: (PasswordItemModel) -> Unit,
    private val onNavigateToChangePasswordComponent: () -> Unit,
) : ComponentContext by componentContext {

    private var _selectedItem = MutableValue(0)
    val selectedItem: Value<Int> = _selectedItem

    fun updateSelectedItem(newItem: Int) {
        _selectedItem.value = newItem
    }

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

    fun openAddPasswordScreen() {
        navigation.replaceAll(Configuration.ScreenAddPassword)
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
                ScreenPasswordComponent(
                    componentContext = context,
                    onNavigateToDetailComponent = {
                        onNavigateToDetailComponent(it)
                    })
            )

            is Configuration.ScreenAddPassword -> Child.ScreenAddPassword(
                ScreenAddPasswordComponent(componentContext = context)
            )

            is Configuration.ScreenSettings -> Child.ScreenSettings(
                ScreenSettingsComponent(
                    componentContext = context,
                    onNavigateToChangePasswordComponent = { onNavigateToChangePasswordComponent() }
                )
            )
        }
    }

    sealed class Child {
        data class ScreenPassword(val component: ScreenPasswordComponent) : Child()
        data class ScreenAddPassword(val component: ScreenAddPasswordComponent) : Child()
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