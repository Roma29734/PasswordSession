package com.pass.word.session.android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.retainedComponent
import com.pass.word.session.android.screen.authenticationScreen.AuthenticationScreen
import com.pass.word.session.android.screen.bottomScreen.BottomMainScreen
import com.pass.word.session.android.screen.bottomScreen.settingsScreen.changePassword.ChangePasswordRoot
import com.pass.word.session.android.screen.detailScreen.DetailScreen
import com.pass.word.session.android.screen.editScreen.EditScreen
import com.pass.word.session.android.screen.initialGreeting.InitialGreetingScreen
import com.pass.word.session.navigation.RootComponent
import com.pass.word.session.ui.MyCustomAppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalDecomposeApi::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val root = retainedComponent {
                RootComponent(it)
            }
            val childStack by root.childStack.subscribeAsState()
            MyCustomAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Children(
                        stack = childStack,
                        animation = stackAnimation(slide())
                    ) { child ->
                        when (val instance = child.instance) {
                            is RootComponent.Child.ScreenBottomMain -> BottomMainScreen(component = instance.component)
                            is RootComponent.Child.ScreenDetail -> DetailScreen(component = instance.component)
                            is RootComponent.Child.ScreenAuthentication -> AuthenticationScreen(
                                component = instance.component
                            )
                            is RootComponent.Child.ScreenEdit -> EditScreen(component = instance.component)
                            is RootComponent.Child.ScreenInitialGreeting -> InitialGreetingScreen(
                                component = instance.component,
                            )
                            is RootComponent.Child.ScreenChangePasswordRootComponent -> ChangePasswordRoot(
                                component = instance.component
                            )
                        }
                    }
                }
            }
        }
    }
}