package screen.initialGreeting.choosingType

import androidx.compose.runtime.Composable
import com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType.ScreenChoosingTypeComponent
import com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType.ScreenChoosingTypeContent
import com.pass.word.session.navigation.screen.initialGreeting.screenChoosingType.ScreenChoosingTypeEvent

@Composable
fun ChoosingTypeScreen(component: ScreenChoosingTypeComponent) {
    ScreenChoosingTypeContent(
        handlerNavToBack = {
            component.onEvent(ScreenChoosingTypeEvent.NavToBack)
        },
        handlerNavToTon = {
            component.onEvent(ScreenChoosingTypeEvent.NavToTonVersion)
        },
        handlerNavToLocal = {
            component.onEvent(ScreenChoosingTypeEvent.NavToLocalVersion)
        }
    )
}