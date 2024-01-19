package com.pass.word.session.android.screen.initialGreeting

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
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
import com.pass.word.session.navigation.screen.main.initialGreeting.InitialGreetingRootComponent
import com.pass.word.session.ui.MyCustomAppTheme

@Composable
fun InitialGreetingScreen(component: InitialGreetingRootComponent) {

    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is InitialGreetingRootComponent.Child.ScreenFirstInitial -> FirstInitialScreen(
                component = instance.component
            )

            is InitialGreetingRootComponent.Child.ScreenEnterInitialPassAuth -> EnterInitialPassAuthScreen(
                component = instance.component
            )

            is InitialGreetingRootComponent.Child.ScreenSecondInitial -> SecondInitialScreen(
                component = instance.component
            )

            is InitialGreetingRootComponent.Child.ScreenImportPassword -> ImportPasswordScreen(
                component = instance.component,
            )
        }
    }

}
