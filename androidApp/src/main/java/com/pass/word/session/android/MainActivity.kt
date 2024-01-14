package com.pass.word.session.android

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                                component = instance.component
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

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // in this method we are checking if the request code
        // which we have passed 101 is same.
        if (requestCode == 101) {
            // if request code is 101 and permissions are granted.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // on below line we are displaying a toast message.
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show()
            } else {
                // on below line we are displaying a toast message.
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun shouldShowRequestPermissionRationale(
        permission: String) : Boolean
    {

        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.shouldShowRequestPermissionRationale(permission)
        } else {
            false
        }
    }

}