package com.pass.word.session.navigation.screen.multiDivisionRoot

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
import com.pass.word.session.navigation.screen.mainApp.changePassword.ChangePasswordRootComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenImportPassword.ScreenImportPasswordComponent
import com.pass.word.session.navigation.screen.localDivisionRoot.LocalDivisionRootComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.ScreenBottomMultiComponent
import com.pass.word.session.navigation.screen.mainApp.detail.ScreenDetailComponent
import com.pass.word.session.navigation.screen.mainApp.edit.ScreenEditComponent
import com.pass.word.session.navigation.screen.mainApp.screenPassKeySettings.ScreenPassKeySettingsComponent
import com.pass.word.session.navigation.screen.mainApp.seedPhraseSettings.ScreenSeedPhraseSettingsComponent
import com.pass.word.session.utilits.StateSelectedType
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.Serializable

class MultiDivisionRootComponent constructor(
    componentContext: ComponentContext,
    private val navToInitScreen: () -> Unit
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
                        navigation.replaceAll(Configuration.ScreenBottomMulti)
                    },
                    onBackHandler = { navigation.pop() }
                )
            )

            is Configuration.ScreenBottomMulti -> Child.ScreenBottomMulti(
                ScreenBottomMultiComponent(
                    componentContext = context,
                    onNavigateToDetailComponent = { model, stateSelectedType ->
                        navigation.pushNew(Configuration.ScreenDetail(model, stateSelectedType))
                    },
                    onNavigateToChangePasswordComponent = {
                        navigation.pushNew(Configuration.ScreenChangePasswordRootComponent)
                    },
                    onNavigateToImportPasswordComponent = {
                        navigation.pushNew(Configuration.ScreenImportPassword)
                    },
                    onNavigateToPhraseSettingsComponent = {
                        navigation.pushNew(Configuration.ScreenSeedPhraseSettings)
                    },
                    onNavigateToPassKeySettingsComponent = {
                        navigation.pushNew(Configuration.ScreenPassKeySettings)
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
                    stateSelectedType = config.stateSelectedType
                )
            )

            is Configuration.ScreenEdit -> Child.ScreenEdit(
                ScreenEditComponent(
                    componentContext = context,
                    passDetailModel = config.passDetailModel,
                    onGoBack = { navigation.pop() },
                    stateSelectedType = config.stateSelectedType
                )
            )

            is Configuration.ScreenSeedPhraseSettings -> Child.ScreenSeedPhraseSettings(
                ScreenSeedPhraseSettingsComponent(
                    componentContext = context,
                    navToBackScreen = { navigation.pop() },
                    navToInitScreen = { navToInitScreen() }
                )
            )

            is Configuration.ScreenPassKeySettings -> Child.ScreenPassKeySettings(
                ScreenPassKeySettingsComponent(
                    componentContext = context,
                    navToBackScreen = {navigation.pop()},
                    navToNextScreen = {
                        navigation.replaceAll(Configuration.ScreenAuthentication)
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

        data class ScreenDetail(val component: ScreenDetailComponent) : Child()

        data class ScreenEdit(val component: ScreenEditComponent) : Child()

        data class ScreenSeedPhraseSettings(val component: ScreenSeedPhraseSettingsComponent) :
            Child()

        data class ScreenPassKeySettings(val component: ScreenPassKeySettingsComponent): Child()
    }

    @Serializable
    sealed class Configuration {


        @Serializable
        data object ScreenAuthentication : Configuration()


        @Serializable
        data object ScreenChangePasswordRootComponent : Configuration()

        @Serializable
        data object ScreenImportPassword : Configuration()

        @Serializable
        data object ScreenBottomMulti : Configuration()

        @Serializable
        data class ScreenDetail(
            val passDetailModel: PasswordItemModel,
            val stateSelectedType: StateSelectedType
        ) : Configuration()

        @Serializable
        data class ScreenEdit(
            val passDetailModel: PasswordItemModel,
            val stateSelectedType: StateSelectedType
        ) : Configuration()

        @Serializable
        data object ScreenSeedPhraseSettings : Configuration()

        @Serializable
        data object ScreenPassKeySettings : Configuration()
    }
}