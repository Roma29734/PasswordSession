package screen.mainApp.bottom.bottomLocal.localPassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenPasswordComponent.LocalPasswordScreenContent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenPasswordComponent.ScreenPasswordComponent

@Composable
fun LocalPasswordScreen(component: ScreenPasswordComponent) {
    val listItemModel: List<PasswordItemModel>? by component.passwordListItem.collectAsState()
    val stateShowedIcon: Boolean by component.stateShowedIcon.subscribeAsState()

    SideEffect {
        component.readBd(DriverFactory())
    }

    LocalPasswordScreenContent(
        listItemModel = listItemModel,
        stateShowedIcon = stateShowedIcon,
        eventComponentDispatch = {
            component.navigateToDetailEvent(it)
        }
    )
}