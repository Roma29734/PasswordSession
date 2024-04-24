package com.pass.word.session.navigation.screen.localDivisionRoot

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.ScreenBottomLocalComponent
import com.pass.word.session.navigation.screen.mainApp.changePassword.ChangePasswordRootComponent
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailComponent
import com.pass.word.session.navigation.screen.mainApp.edit.ScreenEditComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.multiDivisionRoot.MultiDivisionRootComponent
import com.pass.word.session.utilits.StateSelectedType
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.Serializable

class LocalDivisionRootComponent constructor(
    componentContext: ComponentContext,
    private val onNavToInitScreen: () -> Unit
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
            Configuration.ScreenBottomLocal -> Child.ScreenBottomLocal(
                ScreenBottomLocalComponent(
                    componentContext = context,
                    onNavigateToDetailComponent = { model ->
                        navigation.pushNew(Configuration.ScreenDetail(model))
                    },
                    onNavigateToChangePasswordComponent = {
                        navigation.pushNew(Configuration.ScreenChangePasswordRootComponent)
                    },
                    onNavigateToImportPasswordComponent = {
                        navigation.pushNew(Configuration.ScreenImportPassword)
                    },
                    onNavToInitScreen = {
                        onNavToInitScreen()
                    }
                )
            )

            is Configuration.ScreenDetail -> Child.ScreenDetail(
                ScreenDetailComponent(
                    componentContext = context,
                    passDetailModel = config.passDetailModel,
                    onGoBack = {
                        navigation.pop()
                    },
                    onGoEditScreen = { passDetailModel, stateSelectedType ->
                        navigation.pushNew(
                            Configuration.ScreenEdit(
                                passDetailModel,
                                stateSelectedType
                            )
                        )
                    },
                    stateSelectedType = StateSelectedType.LocalStorage
                )
            )

            is Configuration.ScreenAuthentication -> Child.ScreenAuthentication(
                ScreenAuthenticationComponent(
                    componentContext = context,
                    onNavigateToMainScreen = {
                        navigation.replaceCurrent(Configuration.ScreenBottomLocal)
                    }
                )
            )

            is Configuration.ScreenEdit -> Child.ScreenEdit(
                ScreenEditComponent(
                    componentContext = context,
                    passDetailModel = config.passDetailModel,
                    onGoBack = { navigation.pop() },
                    stateSelectedType = StateSelectedType.LocalStorage
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
                    onNextScreen = { navigation.replaceAll(Configuration.ScreenBottomLocal) },
                    onBackHandler = { navigation.pop() }
                )
            )
        }
    }


    sealed class Child {
        data class ScreenBottomLocal(val component: ScreenBottomLocalComponent) : Child()
        data class ScreenDetail(val component: ScreenDetailComponent) : Child()
        data class ScreenAuthentication @OptIn(DelicateCoroutinesApi::class) constructor(val component: ScreenAuthenticationComponent) :
            Child()

        data class ScreenEdit(val component: ScreenEditComponent) : Child()
        data class ScreenChangePasswordRootComponent(val component: ChangePasswordRootComponent) :
            Child()

        data class ScreenImportPassword(val component: ScreenImportPasswordComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ScreenBottomLocal : Configuration()

        @Serializable
        data class ScreenDetail(val passDetailModel: PasswordItemModel) : Configuration()

        @Serializable
        data object ScreenAuthentication : Configuration()

        @Serializable
        data class ScreenEdit(
            val passDetailModel: PasswordItemModel,
            val stateSelectedType: StateSelectedType
        ) : Configuration()

        @Serializable
        data object ScreenChangePasswordRootComponent : Configuration()

        @Serializable
        data object ScreenImportPassword : Configuration()
    }
}