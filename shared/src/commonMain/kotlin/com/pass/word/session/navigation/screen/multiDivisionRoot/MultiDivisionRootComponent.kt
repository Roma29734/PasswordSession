package com.pass.word.session.navigation.screen.multiDivisionRoot

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.navigation.screen.mainApp.changePassword.ChangePasswordRootComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.localDivisionRoot.LocalDivisionRootComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.ScreenBottomMultiComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.Serializable

class MultiDivisionRootComponent constructor(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()


    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenAuthentication,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class, DelicateCoroutinesApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {


            is Configuration.ScreenAuthentication -> Child.ScreenAuthentication(
                ScreenAuthenticationComponent(
                    componentContext = context,
                    onNavigateToMainScreen = {
                        navigation.replaceCurrent(Configuration.ScreenBottomMulti)
                    }
                )
            )


            is Configuration.ScreenChangePasswordRootComponent -> Child.ScreenChangePasswordRootComponent(
                ChangePasswordRootComponent(
                    componentContext = context,
                    onBackNavigation = { navigation.pop() },
                    onNextNavigation = {
                        navigation.replaceAll(Configuration.ScreenAuthentication)
                    }
                )
            )

            is Configuration.ScreenImportPassword -> Child.ScreenImportPassword(
                ScreenImportPasswordComponent(
                    componentContext = context,
                    onNextScreen = {
//                        navigation.replaceAll(Configuration.ScreenBottomMain)
                    },
                    onBackHandler = { navigation.pop() }
                )
            )

            is Configuration.ScreenBottomMulti -> Child.ScreenBottomMulti(
                ScreenBottomMultiComponent(
                    componentContext = context,
                    onNavigateToDetailComponent = { model ->
//                        navigation.pushNew(Configuration.ScreenDetail(model))
                    },
                    onNavigateToChangePasswordComponent = {
                        navigation.pushNew(Configuration.ScreenChangePasswordRootComponent)
                    },
                    onNavigateToImportPasswordComponent = {
                        navigation.pushNew(Configuration.ScreenImportPassword)
                    }
                )
            )

        }
    }


    sealed class Child {
        data class ScreenAuthentication @OptIn(DelicateCoroutinesApi::class) constructor(val component: ScreenAuthenticationComponent) :
            Child()

        data class ScreenChangePasswordRootComponent(val component: ChangePasswordRootComponent) :
            Child()

        data class ScreenImportPassword(val component: ScreenImportPasswordComponent) : Child()

        data class ScreenBottomMulti(val component: ScreenBottomMultiComponent) : Child()
    }

    @Serializable
    sealed class Configuration {


        @Serializable
        data object ScreenAuthentication : Configuration()


        @Serializable
        data object ScreenChangePasswordRootComponent : Configuration()

        @Serializable
        data object ScreenImportPassword : Configuration()

        data object ScreenBottomMulti : Configuration()
    }
}