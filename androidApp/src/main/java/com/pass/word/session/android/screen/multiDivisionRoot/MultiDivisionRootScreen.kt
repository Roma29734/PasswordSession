package com.pass.word.session.android.screen.multiDivisionRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.initialGreeting.ImportPasswordScreen
import com.pass.word.session.android.screen.mainApp.authenticationScreen.AuthenticationScreen
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.BottomMultiScreen
import com.pass.word.session.android.screen.mainApp.changePassword.ChangePasswordRoot
import com.pass.word.session.android.screen.mainApp.detailScreen.DetailScreen
import com.pass.word.session.android.screen.mainApp.editScreen.EditScreen
import com.pass.word.session.navigation.screen.multiDivisionRoot.MultiDivisionRootComponent
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MultiDivisionRootScreen(component: MultiDivisionRootComponent) {

    val childStack by component.childStack.subscribeAsState()
    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is MultiDivisionRootComponent.Child.ScreenAuthentication -> AuthenticationScreen(
                component = instance.component
            )
            is MultiDivisionRootComponent.Child.ScreenChangePasswordRootComponent -> ChangePasswordRoot(
                component = instance.component
            )
            is MultiDivisionRootComponent.Child.ScreenImportPassword -> ImportPasswordScreen(
                component = instance.component
            )
            is MultiDivisionRootComponent.Child.ScreenBottomMulti -> BottomMultiScreen(component = instance.component)
            
            is MultiDivisionRootComponent.Child.ScreenDetail -> DetailScreen(component = instance.component)
            
            is MultiDivisionRootComponent.Child.ScreenEdit -> EditScreen(component = instance.component)
        }
    }
}