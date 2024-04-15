package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.MainActivity
import com.pass.word.session.android.R
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.ScreensBottom
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.addPasswordScreen.AppPasswordScreen
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.passwordScreen.PasswordScreen
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.settingsScreen.SettingsScreen
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.addMultiPassword.AddMultiPasswordScreen
import com.pass.word.session.android.screen.mainApp.bottomScreen.bottomMulti.tonPassword.TonPasswordScreen
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.ScreenBottomLocalComponent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.ScreenBottomMultiComponent
import com.pass.word.session.ui.CustomColor

@Composable
fun BottomMultiScreen(component: ScreenBottomMultiComponent) {

    val selectedItem by component.selectedItem.subscribeAsState()
    val context = LocalContext.current as MainActivity
    val screens by remember {
        mutableStateOf(
            listOf(
                ScreensBottom("Password", {component.openPasswordScreen()}, false),
                ScreensBottom("Add Password", {component.openAddPasswordScreen()}, false),
                ScreensBottom("Settings", {component.openSettingsScreen()}, false)
            )
        )
    }

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(selectedItem >= 1) {
                    component.updateSelectedItem(selectedItem - 1)
                    screens[selectedItem].openScreen()
                    Log.d("BottomMultiScreen", "back handler")
                } else {
                    context.finish()
                }
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = true
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, backDispatcher) {
        // Add callback to the backDispatcher
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        // When the effect leaves the Composition, remove the callback
        onDispose {
            backCallback.remove()
        }
    }


    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                actions = {
                    screens.forEachIndexed { index, screensBottom ->
                        NavigationBarItem(
                            icon = {
                                when (screensBottom.name) {
                                    "Password" -> Icon(
                                        painter = painterResource(id = R.drawable.ic_password),
                                        contentDescription = null,
                                    )

                                    "Add Password" -> Icon(
                                        Icons.Default.Add,
                                        contentDescription = null
                                    )

                                    "Settings" -> Icon(
                                        Icons.Default.Settings,
                                        contentDescription = null
                                    )
                                }
                            },
                            label = { Text(text = screensBottom.name) },
                            selected = selectedItem == index,
                            onClick = {
                                component.updateSelectedItem(index)
                                screensBottom.openScreen()
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = CustomColor().brandBlueLight,
                                unselectedTextColor = CustomColor().grayLight,
                                selectedTextColor = CustomColor().brandBlueLight,
                                unselectedIconColor = CustomColor().grayLight,
                                indicatorColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.background,
            )
        },
        content = { innerpadding ->

            Column(modifier = Modifier.padding(innerpadding)) {
                Children(
                    stack = component.childStack,
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                    animation = stackAnimation(fade() + scale()),
                ) { child ->
                    when (val instance = child.instance) {
                        is ScreenBottomMultiComponent.Child.ScreenPassword -> TonPasswordScreen(instance.component)
                        is ScreenBottomMultiComponent.Child.ScreenAddPassword -> AddMultiPasswordScreen(
                            instance.component
                        )

                        is ScreenBottomMultiComponent.Child.ScreenSettings -> SettingsScreen(instance.component)
                    }
                }
            }
        },
    )
}

