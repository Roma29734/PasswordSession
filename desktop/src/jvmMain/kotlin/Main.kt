import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.pass.word.session.navigation.RootComponent
import com.pass.word.session.ui.MyCustomAppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import screen.authenticationScreen.AuthenticationScreen
import screen.bottomScreen.BottomMainScreen
import screen.changePassword.ChangePasswordRoot
import screen.detailScreen.DetailScreen
import screen.editScreen.EditScreen
import screen.importPasswordScreen.ImportPasswordScreen
import screen.initialGreetingScreen.InitialGreetingScreen
import javax.swing.SwingUtilities

@OptIn(ExperimentalDecomposeApi::class, DelicateCoroutinesApi::class)
fun main() {

    val lifecycle = LifecycleRegistry()
    val root = runOnMainThreadBlocking {  RootComponent(DefaultComponentContext(lifecycle)) }
    application {
        val windowState = rememberWindowState(size = DpSize(1700.dp, 1600.dp))
        val childStack by root.childStack.subscribeAsState()
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Password Session"
        ) {
            MyCustomAppTheme{
                LifecycleController(lifecycle, windowState)
                Surface(modifier = Modifier.fillMaxSize()) {
                    Children(
                        stack = childStack,
                        animation = stackAnimation(slide())
                    ) { child ->

                        when(val instance = child.instance) {
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

                            is RootComponent.Child.ScreenImportPassword -> ImportPasswordScreen(
                                component = instance.component
                            )
                        }
                    }
                }
            }

        }
    }
}

private inline fun <T : Any> runOnMainThreadBlocking(crossinline block: () -> T): T {
    lateinit var result: T
    SwingUtilities.invokeAndWait { result = block() }
    return result
}