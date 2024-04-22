package com.pass.word.session.android.screen.mainApp.enterPassScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.pass.word.session.android.screen.viewComponent.BoxItemCode
import com.pass.word.session.android.screen.viewComponent.ButtonNumber
import com.pass.word.session.android.screen.viewComponent.UpBarButtonBack
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.EnterPassScreenContent
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.ScreenEnterPassComponent
import com.pass.word.session.navigation.screen.mainApp.screenEnterPass.ScreenEnterPassEvent
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EnterPassScreen(component: ScreenEnterPassComponent) {


    val context = LocalContext.current

    val passItem: String by component.passItem.subscribeAsState()
    val passEnterState by component.stateEnterPass.collectAsState()
    val scope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }

    DisposableEffect(component) {
        val listenerSnackBarShow: (String) -> Unit = {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }
        component.subscribeListenerSnackBar(listenerSnackBarShow)

        onDispose {
            // Отписка при уничтожении экрана
            component.unsubscribeListenerSnackBar(listenerSnackBarShow)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        EnterPassScreenContent(
            passItem = passItem,
            passEnterState = passEnterState,
            context = context,
            eventComponentDispatch = {
                component.onEvent(it)
            }
        )

    }
}