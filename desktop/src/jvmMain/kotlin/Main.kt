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
import com.pass.word.session.navigation.screen.mainApp.authentication.ScreenAuthenticationComponent
import com.pass.word.session.ui.MyCustomAppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import screen.initialGreeting.InitialGreetingScreen
import screen.localDivisionRoot.LocalDivisionRootScreen
import screen.multiDivisionRoot.MultiDivisionRootScreen
import javax.swing.SwingUtilities

@OptIn(ExperimentalDecomposeApi::class, DelicateCoroutinesApi::class)
fun main() {

    val lifecycle = LifecycleRegistry()
    val root = runOnMainThreadBlocking {  RootComponent(DefaultComponentContext(lifecycle)) }
    application {
        val windowState = rememberWindowState(size = DpSize(500.dp, 800.dp))
        val childStack by root.childStack.subscribeAsState()
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Password Session",
            resizable = false
        ) {
            MyCustomAppTheme(
                darkTheme = true,
                screenWidthDp = windowState.size.width,
                screenHeightDp = windowState.size.height,
            ) {
                LifecycleController(lifecycle, windowState)
                Surface(modifier = Modifier.fillMaxSize()) {
                    Children(
                        stack = childStack,
                        animation = stackAnimation(slide())
                    ) { child ->
                        when(val instance = child.instance) {
                            is RootComponent.Child.ScreenInitialGreeting -> InitialGreetingScreen(component = instance.component)
                            is RootComponent.Child.LocalDivisionRoot -> LocalDivisionRootScreen(component = instance.component)
                            is RootComponent.Child.MultiDivisionRoot -> MultiDivisionRootScreen(
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