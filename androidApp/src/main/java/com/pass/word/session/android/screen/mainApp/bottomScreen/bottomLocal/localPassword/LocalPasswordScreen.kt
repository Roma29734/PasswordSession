package com.pass.word.session.android.screen.mainApp.bottomScreen.bottomLocal.localPassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.data.DriverFactory
import com.pass.word.session.data.model.PasswordItemModel
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenPasswordComponent.LocalPasswordScreenContent
import com.pass.word.session.navigation.screen.mainApp.bottomMain.bottomLocal.screenPasswordComponent.ScreenPasswordComponent


@Composable
fun LocalPasswordScreen(component: ScreenPasswordComponent) {
    val context = LocalContext.current
    val listItemModel: List<PasswordItemModel>? by component.passwordListItem.collectAsState()
    val stateShowedIcon: Boolean by component.stateShowedIcon.subscribeAsState()

    SideEffect {
        component.readBd(DriverFactory(context))
    }

    LocalPasswordScreenContent(
        listItemModel = listItemModel,
        stateShowedIcon = stateShowedIcon,
        eventComponentDispatch = {
            component.navigateToDetailEvent(it)
        }
    )
}

