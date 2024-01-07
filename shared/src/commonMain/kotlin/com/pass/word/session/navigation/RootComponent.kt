package com.pass.word.session.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.pass.word.session.navigation.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.main.bottomMain.ScreenBottomMainComponent
import com.pass.word.session.navigation.screen.main.detail.ScreenDetailComponent
import kotlinx.serialization.Serializable

class RootComponent constructor(
    componentContext: ComponentContext
) : ComponentContext by componentContext {
    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenBottomMain,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            Configuration.ScreenBottomMain -> Child.ScreenBottomMain(
                ScreenBottomMainComponent(
                    componentContext = context,
                    onNavigateToDetailComponent = { model ->
                        navigation.pushNew(Configuration.ScreenDetail(model))
                    }
                )
            )

            is Configuration.ScreenDetail -> Child.ScreenDetail(
                ScreenDetailComponent(
                    componentContext = context,
                    passDetailModel = config.passDetailModel,
                    onGoBack = {
                        navigation.pop()
                    }
                )
            )
        }
    }

    sealed class Child {
        data class ScreenBottomMain(val component: ScreenBottomMainComponent) : Child()
        data class ScreenDetail(val component: ScreenDetailComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ScreenBottomMain : Configuration()
        @Serializable
        data class ScreenDetail(val passDetailModel: PasswordItemModel) : Configuration()
    }
}