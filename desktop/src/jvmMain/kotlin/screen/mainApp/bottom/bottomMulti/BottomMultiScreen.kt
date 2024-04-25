package screen.mainApp.bottom.bottomMulti

import Img.MyIconPack
import Img.myiconpack.IcPassword
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomMulti.ScreenBottomMultiComponent
import com.pass.word.session.ui.CustomColor
import screen.mainApp.bottom.bottomMulti.addMultiPassword.AddMultiPasswordScreen
import screen.mainApp.bottom.bottomMulti.multiPass.MultiPasswordScreen
import screen.mainApp.bottom.settings.SettingsScreen

@Composable
fun  BottomMultiScreen(component: ScreenBottomMultiComponent) {
    val selectedItem by component.selectedItem.subscribeAsState()
    val screens by remember {
        mutableStateOf(
            listOf(
                ScreensBottom("Password", {component.openPasswordScreen()}, false),
                ScreensBottom("Add Password", {component.openAddPasswordScreen()}, false),
                ScreensBottom("Settings", {component.openSettingsScreen()}, false)
            )
        )
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
                                        imageVector = MyIconPack.IcPassword,
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
                        is ScreenBottomMultiComponent.Child.ScreenPassword -> MultiPasswordScreen(instance.component)
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


data class ScreensBottom(val name: String, val openScreen: () -> Unit, val isSelected: Boolean)