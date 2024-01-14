package com.pass.word.session.android.screen.bottomScreen.settingsScreen.changePassword

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.navigation.screen.main.changePassword.ChangePasswordRootComponent

@Composable
fun ChangePasswordRoot(component: ChangePasswordRootComponent) {
    val childStack by component.childStack.subscribeAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is ChangePasswordRootComponent.Child.ScreenWarning -> WarningScreen(
                    component = instance.component
                )

                is ChangePasswordRootComponent.Child.ScreenChangePassword -> ChangePasswordScreen(
                    component = instance.component
                )

            }
        }
    }
}